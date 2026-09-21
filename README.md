# FoodStore - Backend en Java

Proyecto académico que modela una tienda mediante programación orientada a objetos. El sistema permite crear usuarios, categorías, productos y pedidos; controlar el stock; calcular el total de cada pedido y mostrar la información mediante `toString()`.

Las asociaciones múltiples utilizan colecciones `Set`, implementadas principalmente mediante `LinkedHashSet`. De esta manera no se admiten elementos repetidos según los contratos de `equals()` y `hashCode()`, y se conserva el orden de inserción al recorrer las colecciones.

## Autor

**Sebastian Schneider**

## Estructura del proyecto

```text
src/
├── README.md
└── main/
    └── java/
        └── com/tup/programacion3/
            ├── Main.java
            ├── entities/
            │   ├── Base.java
            │   ├── Categoria.java
            │   ├── DetallePedido.java
            │   ├── Pedido.java
            │   ├── Producto.java
            │   └── Usuario.java
            ├── enums/
            │   ├── Estado.java
            │   ├── FormaPago.java
            │   └── Rol.java
            ├── exceptions/
            │   ├── EntidadNoEncontradaException.java
            │   ├── NegocioException.java
            │   ├── ProductoNoDisponibleException.java
            │   ├── StockInsuficienteException.java
            │   └── ValorInvalidoException.java
            └── interfaces/
                └── Calculable.java
```

## Relación entre las clases

```text
Base
├── Categoria ── contiene un Set<Producto>
├── Producto  ── pertenece a una Categoria
├── Usuario   ── contiene un Set<Pedido>
├── Pedido    ── pertenece a un Usuario
│               contiene un Set<DetallePedido>
│               implementa Calculable
└── DetallePedido ── referencia un Producto
```

Las relaciones principales son:

- Una `Categoria` puede contener varios productos y cada `Producto` puede pertenecer a una categoría.
- Un `Usuario` puede tener varios pedidos y cada `Pedido` pertenece a un usuario.
- Un `Pedido` puede contener varios detalles.
- Cada `DetallePedido` representa un producto, una cantidad y el subtotal correspondiente.
- `Pedido` implementa `Calculable` para calcular el total sumando los subtotales de sus detalles.

## Funcionamiento de las clases

### `Main`

Es el punto de entrada de la aplicación. Se encarga de:

1. Instanciar usuarios, pedidos, categorías y productos.
2. Incorporar productos y cantidades a los pedidos.
3. Mostrar un producto individual mediante `toString()`.
4. Recorrer y mostrar el conjunto global de productos cargados.
5. Buscar al usuario que posee la mayor cantidad de pedidos y mostrar esos pedidos.
6. Crear un producto auxiliar con el mismo `id` que otro producto y compararlo con toda la colección mediante `equals()`.

### `Base`

Es la clase abstracta de la que heredan todas las entidades. Contiene los atributos comunes:

- `id`: identificador de la entidad, generado de manera consecutiva.
- `eliminado`: indica si la entidad fue eliminada de manera lógica.
- `createdAt`: fecha y hora de creación.

También obliga a las clases hijas a implementar su propia representación mediante `toString()`.

### `Categoria`

Representa una categoría de productos, con un nombre y una descripción. Mantiene sus productos en un `Set<Producto>`.

El método `agregarProducto()` evita duplicados y mantiene la relación bidireccional: al agregar un producto a la categoría, también se asigna esa categoría al producto.

Su `toString()` muestra el identificador, el nombre, la descripción y la cantidad de productos asociados. Su comparación mediante `equals()` utiliza el nombre y la descripción.

### `Producto`

Representa un artículo disponible en la tienda. Contiene nombre, precio, descripción, stock, disponibilidad y categoría.

La clase mantiene un `Set<Producto>` estático que registra los productos creados mediante el constructor completo. Ese conjunto puede consultarse con `Producto.getProductos()`.

Al asignar una categoría mediante `setCategoria()`, el producto también se incorpora al conjunto de productos de esa categoría. El precio y el stock son validados para impedir valores negativos.

Su `equals()` y su `hashCode()` utilizan el `id`, por lo que dos productos se consideran iguales cuando poseen el mismo identificador. Su `toString()` muestra sus datos principales.

### `Usuario`

Representa a una persona que utiliza el sistema. Contiene nombre, apellido, correo electrónico, celular, contraseña y un `Rol`.

Cada usuario posee un `Set<Pedido>`. Al relacionar un pedido con un usuario, ambos lados de la asociación se actualizan. Además, la clase mantiene un conjunto estático de usuarios, accesible mediante `Usuario.getUsuarios()`, que permite recorrer todos los usuarios cargados.

Su `toString()` presenta el nombre, el correo y el rol. `equals()` y `hashCode()` comparan los datos personales y el rol.

### `Pedido`

Representa una compra realizada por un usuario. Contiene fecha, estado, forma de pago, total, usuario y un `Set<DetallePedido>`.

Cuando se agrega un producto mediante `addDetallePedido()`:

1. Se verifica que el producto exista, esté disponible y tenga stock suficiente.
2. Si el producto ya se encuentra en el pedido, se incrementa su cantidad.
3. Si todavía no existe, se crea un nuevo `DetallePedido`.
4. Se descuenta la cantidad solicitada del stock del producto.
5. Se vuelve a calcular el total del pedido.

Cuando se elimina un detalle, el stock se reintegra al producto y el total vuelve a calcularse. El método `toString()` presenta la cabecera del pedido, todos sus detalles y el total.

### `DetallePedido`

Representa una línea dentro de un pedido. Relaciona un `Producto` con una cantidad y calcula su subtotal mediante:

```text
subtotal = cantidad × precio del producto
```

Cuando cambia la cantidad o el producto, el subtotal se actualiza. No se admiten cantidades menores o iguales a cero ni productos nulos. Su `equals()` y su `hashCode()` utilizan el identificador.

### `Calculable`

Es una interfaz que declara el método:

```java
void calcularTotal();
```

La clase `Pedido` implementa esta interfaz sumando el subtotal de todos sus detalles.

## Enumeraciones

### `Estado`

Define los estados posibles de un pedido:

- `CONFIRMADO`
- `PENDIENTE`
- `TERMINADO`
- `CANCELADO`

### `FormaPago`

Define las formas de pago disponibles:

- `EFECTIVO`
- `TARJETA`
- `TRANSFERENCIA`

### `Rol`

Define los roles de los usuarios:

- `ADMIN`
- `USER`

## Excepciones

Todas las excepciones de negocio heredan de `NegocioException`, que a su vez extiende `RuntimeException`.

- `NegocioException`: excepción base para las reglas del dominio.
- `EntidadNoEncontradaException`: indica que no se encontró una entidad requerida.
- `ProductoNoDisponibleException`: se lanza al intentar agregar un producto no disponible.
- `StockInsuficienteException`: se lanza cuando la cantidad solicitada supera el stock.
- `ValorInvalidoException`: se utiliza para precios, cantidades, stock u otros valores inválidos.

## Uso de `Set`, `equals()` y `hashCode()`

Las colecciones `Set` determinan si un elemento ya existe utilizando `equals()` y `hashCode()`. Por ello, ambos métodos deben ser coherentes: si dos objetos son iguales según `equals()`, deben producir el mismo código hash.

En las entidades cuyo `equals()` se basa en el identificador, el `id` debe permanecer estable mientras el objeto se encuentre dentro de un conjunto. Esto permite evitar duplicados y realizar búsquedas o eliminaciones correctamente.
