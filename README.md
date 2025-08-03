# kafka-learning

## Multi-Module Project Setup

1. Create a parent project (`kafka-learning`).
2. Inside the parent, create the following modules:
    - `kafka-producer`
    - `kafka-consumer`
    - `kafka-common`
3. In `settings.gradle` of the parent project, include the modules:
    ```
    include('kafka-common', 'kafka-producer', 'kafka-consumer')
    ```
4. Place shared classes (e.g., `User`) in `kafka-common`.
5. In the `build.gradle` files of both producer and consumer modules, add:
    ```
    implementation project(':kafka-common')
    ```
6. If using composite builds, add the following to the `settings.gradle` of producer and consumer modules:
    ```
    includeBuild('../kafka-common')
    ```


