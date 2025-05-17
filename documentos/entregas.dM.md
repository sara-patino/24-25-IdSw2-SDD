# Diseño Modular

Este documento describe el diseño modular del sistema desarrollado para la gestión de préstamos de una biblioteca.

## Módulos del Sistema

### 1. Modelo

Contiene la representación de los datos y la lógica de negocio.

#### 1.1 `Document` (abstracta)

* **Subclases:** `Book`, `Magazine`, `Audiovisual`
* **Responsabilidad:** Representar los diferentes tipos de materiales disponibles en la biblioteca.
* **Métodos clave:** `isAvailable()`, `setAvailable()`, `getTitle()`

#### 1.2 `Loan`

* **Responsabilidad:** Representar los préstamos a realizar.
* **Métodos clave:** `processReturn()`, `display()`, `getUser()`

#### 1.3 `User`

* **Responsabilidad:** Representar a los usuarios de la biblioteca.
* **Métodos clave:** `getId()`, `display()`

#### 1.4 `Loanable` (interfaz)

* **Responsabilidad:** Proveer una interfaz para objetos que pueden ser prestados.
* **Implementada por:** `Material`, `Document`

### 2. Vista  
Contiene la interfaz de usuario.

#### 2.1 `Main`  
- **Responsabilidad:** Mostrar el menú principal, leer la entrada del usuario y delegar las acciones al controlador.  
- **Métodos clave:**  
  - `main(String[] args)`: punto de entrada al programa, ejecuta el bucle principal del menú.  
  - Presenta opciones de acuerdo al tipo de usuario.

### 3. Controlador

Contiene la lógica que coordina la interacción entre la Vista y el Modelo.

#### 3.1 `LibraryManager`

* **Responsabilidad:** Gestionar la colección de materiales, usuarios y préstamos.
* **Métodos clave:**

  * `addUser()`
  * `addMaterial()`
  * `createLoan()`
  * `returnLoan()`
  * `searchMaterial()`
  * `displayLoans()`

## Análisis de la Modularización

* **Separación de responsabilidades:** Cada módulo tiene una función claramente definida.
* **Fácil mantenibilidad:** Cambios en la Vista o en la lógica de negocio no afectan al resto del sistema.
* **Bajo acoplamiento y alta cohesión:** Los módulos dependen mínimamente unos de otros y cada uno agrupa funcionalidades relacionadas.

## Cumplimiento de buenas prácticas y ausencia de smells code

* Se han aplicado los principios SOLID, en especial:

  * **Responsabilidad Única**: cada clase tiene un único propósito.
  * **Abierto/Cerrado**: el uso de clases abstractas y la interfaz `Loanable` permite extender funcionalidades sin modificar código existente.
* Se han evitado:

  * Métodos largos: todos los métodos son breves y legibles.
  * Clases con demasiadas responsabilidades: cada clase se centra en un único concepto del dominio.
  * Código duplicado: se utiliza herencia y polimorfismo para reutilizar lógica.

