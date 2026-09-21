package com.tup.programacion3.entities;

import com.tup.programacion3.enums.FormaPago;
import com.tup.programacion3.enums.Estado;
import com.tup.programacion3.interfaces.Calculable;
import com.tup.programacion3.exceptions.EntidadNoEncontradaException;
import com.tup.programacion3.exceptions.ProductoNoDisponibleException;
import com.tup.programacion3.exceptions.StockInsuficienteException;
import com.tup.programacion3.exceptions.ValorInvalidoException;

import java.util.LinkedHashSet;
import java.util.Set;
import java.time.LocalDate;
import java.util.Objects;


public class Pedido extends Base implements Calculable {
    private LocalDate fecha;

    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Set<DetallePedido> detalles;
    private Usuario usuario;

    public Pedido() {
        detalles = new LinkedHashSet<>();
        total = 0.0;
        fecha = LocalDate.now();
        estado = Estado.PENDIENTE;
    }

    public Pedido(FormaPago formaPago, Usuario usuario) {
        this();
        this.formaPago = formaPago;
        setUsuario(usuario);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Set<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(Set<DetallePedido> detalles) {
        if (detalles == null) {
            throw new ValorInvalidoException("detalles", "el conjunto no puede ser null");
        }
        this.detalles = new LinkedHashSet<>(detalles);
        calcularTotal();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (usuario != null && !usuario.getPedidos().contains(this)) {
            usuario.agregarPedido(this);
        }
    }

    @Override
    public void calcularTotal() {
        this.total = 0.0;
        for (DetallePedido detalle : detalles) {
            if (detalle != null && detalle.getSubtotal() != null) {
                this.total += detalle.getSubtotal();
            }
        }
    }

    public void addDetallePedido(int cantidad, Producto producto) {
        validarDetalle(cantidad, producto);
        DetallePedido existente = findDetallePedidoByProducto(producto);
        if (existente != null) {
            detalles.remove(existente);
            existente.setCantidad(existente.getCantidad() + cantidad);
            detalles.add(existente);
        } else {
            DetallePedido dp = new DetallePedido(cantidad, producto);
            detalles.add(dp);
        }
        producto.setStock(producto.getStock() - cantidad);
        calcularTotal();
    }

    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        if (producto == null || producto.getId() == null) {
            return null;
        }
        return detalles.stream().filter(dp -> dp.getProducto() != null && dp.getProducto().getId() != null
                && dp.getProducto().getId().equals(producto.getId())).findFirst().orElse(null);
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        if (producto == null) {
            throw new EntidadNoEncontradaException("Producto");
        }
        DetallePedido detalle = findDetallePedidoByProducto(producto);
        if (detalle == null) {
            throw new EntidadNoEncontradaException("DetallePedido");
        }
        producto.setStock(producto.getStock() + detalle.getCantidad());
        detalles.remove(detalle);
        calcularTotal();
    }

    private void validarDetalle(int cantidad, Producto producto) {
        if (producto == null) {
            throw new EntidadNoEncontradaException("Producto");
        }
        if (cantidad <= 0) {
            throw new ValorInvalidoException("cantidad", "debe ser mayor a cero");
        }
        if (!producto.isDisponible()) {
            throw new ProductoNoDisponibleException(producto);
        }
        if (producto.getStock() < cantidad) {
            throw new StockInsuficienteException(producto, cantidad);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("  > Pedido #%d | Fecha: %s | Estado: %s | FormaPago: %s\n",
                getId(), fecha, estado, formaPago));
        sb.append("    -------------------------------------------------------\n");
        detalles.forEach(d -> sb.append(d.toString()).append("\n"));
        sb.append(String.format("    TOTAL DEL PEDIDO: $%.2f\n", total));
        sb.append("    -------------------------------------------------------\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(getFecha(), pedido.getFecha()) && getEstado() == pedido.getEstado() && Objects.equals(getTotal(), pedido.getTotal()) && getFormaPago() == pedido.getFormaPago() && Objects.equals(getDetalles(), pedido.getDetalles()) && Objects.equals(getUsuario(), pedido.getUsuario());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFecha(), getEstado(), getTotal(), getFormaPago(), getDetalles(), getUsuario());
    }
}
