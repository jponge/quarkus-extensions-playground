# Quarkus extensions playground

## Basic extension

* Injects a RestEasy endpoint
* Custom "greetings" build item, greeting end up in a resource file
* Testing is shown

## Greetings lib extension

* Third-party library
* Expose as a (synthetic) bean with build-time provided values
* Show build / runtime recorders

## Configuration extension

* Defining build-time configuration
* Recording a configuration model
* Injecting a non-application (Vert.x) route

## Reflection extension

* Register a class for reflective access
* Bytecode engineering with ASM
