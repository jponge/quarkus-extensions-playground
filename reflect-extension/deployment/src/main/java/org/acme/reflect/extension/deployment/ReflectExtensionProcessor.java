package org.acme.reflect.extension.deployment;

import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.BytecodeTransformerBuildItem;
import io.quarkus.deployment.builditem.CombinedIndexBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.IndexDependencyBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;
import io.quarkus.gizmo.Gizmo;
import org.acme.Greeter;
import org.acme.yolo.ReflectAndGreet;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.jandex.IndexView;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiFunction;

class ReflectExtensionProcessor {

    private static final String FEATURE = "reflect-extension";

    private static final Logger logger = LoggerFactory.getLogger(ReflectExtensionProcessor.class);

    @BuildStep
    FeatureBuildItem feature() {
        logger.info(FEATURE);
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    AdditionalBeanBuildItem registerEndpoint() {
        logger.info("Adding our API endpoint");
        return AdditionalBeanBuildItem.builder()
                .setUnremovable()
                .addBeanClass(ReflectAndGreet.class)
                .build();
    }

    @BuildStep
    ReflectiveClassBuildItem registerGreeterForReflection() {
        logger.info("Register Greeter for reflection");
        return ReflectiveClassBuildItem.builder(Greeter.class)
                .methods(true)
                .build();
    }

    @BuildStep
    IndexDependencyBuildItem registerGreeterLib() {
        logger.info("Adding greetings lib to Jandex");
        return new IndexDependencyBuildItem("org.acme", "greetings-lib");
    }

    @BuildStep
    void instrumentGreeterImplementations(CombinedIndexBuildItem combinedIndexBuildItem, BuildProducer<BytecodeTransformerBuildItem> producer) {
        IndexView index = combinedIndexBuildItem.getIndex();
        for (ClassInfo implementor : index.getAllKnownImplementors(DotName.createSimple("org.acme.Greeter"))) {

            String klass = implementor.name().toString();
            logger.info("Instrumenting {}", klass);

            producer.produce(new BytecodeTransformerBuildItem(klass, new BiFunction<String, ClassVisitor, ClassVisitor>() {
                @Override
                public ClassVisitor apply(String cls, ClassVisitor classVisitor) {
                    return new ClassVisitor(Gizmo.ASM_API_VERSION, classVisitor) {
                        @Override
                        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {

                            MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                            if (name.equals("<init>") || name.equals("<clinit>")) {
                                return mv;
                            }

                            return new MethodVisitor(Gizmo.ASM_API_VERSION, mv) {
                                @Override
                                public void visitCode() {
                                    logger.info("Doing magic on method {} of class {}", name, cls);

                                    visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                                    visitLdcInsn(">>> Entering :: " + name);
                                    visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

                                    super.visitCode();

                                    visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                                    visitLdcInsn("<<< Exiting :: " + name);
                                    visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                                }
                            };
                        }
                    };
                }
            }));
        }
    }
}
