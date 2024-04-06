![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# Overview
## What is Monadic
Monadic is a distributed multiplatform Kotlin framework that provides a way to write code from functional programming.

### What is distributed framework
Monadic allows you to include only the part of functional programming that you plan to use in the project.
You do not need to connect everything if you do not plan to use all the functionality.

# Setup
## Gradle
Modules are published in Maven Central:
- `core-function` Basic operations on functions (composition, currying)
- `monad-either` The monad Either

Add the necessary modules to the build.gradle file of your module:

```gradle
implementation "io.github.pavelannin:monadic-<module-name>:<version>"
```

