package com.ws.masterserver.repository;

import com.ws.masterserver.dto.admin.order.detail.StatusDto;
import com.ws.masterserver.entity.OrderStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatusEntity, String> {

    @Query("select new com.ws.masterserver.dto.admin.order.detail.StatusDto(\n" +
            "os.status,\n" +
            "os.createdDate,\n" +
            "u.role,\n" +
            "concat(u.firstName, ' ', u.lastName))\n" +
            "from OrderStatusEntity os\n" +
            "left join UserEntity u on u.id = os.createdBy\n" +
            "where os.orderId = :orderId\n" +
            "order by os.createdDate")
    List<StatusDto> findHistory(@Param("orderId") String orderId);
}
