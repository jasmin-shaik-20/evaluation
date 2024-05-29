package com.example.evaluation.repository;

import com.example.evaluation.entity.OrderItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems,Integer> {
    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    default int updateEntityProperty(int id, int quantity,int product_id) {
        String sql = "UPDATE order_items SET quantity = ? WHERE order_id = ?and productId=?";
        return jdbcTemplate.update(sql,quantity , id);
    }

    public default int deleteEntity(int orderId, int productId) {
        String sql = "DELETE FROM order_items WHERE order_id = ? AND product_id = ?";
        return jdbcTemplate.update(sql, orderId, productId);
    }
}
