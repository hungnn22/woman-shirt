package com.ws.masterserver.repository;

import com.ws.masterserver.dto.admin.dashboard.EarningDayDto;
import com.ws.masterserver.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    @Query("select o.shipPrice from OrderEntity o where o.id = ?1")
    Long findPriceShipById(String id);

    @Query("select count(o.id) from OrderEntity o\n" +
            "where cast(o.createdDate as date) = cast(current_date as date)")
    Long getNumberToday();

    /**
     * @return doanh thu trong tuần
     */
    @Query("select sum(o.total)\n" +
            "from OrderEntity o\n" +
            "where o.payed = true\n" +
            "and extract('week' from o.createdDate) = extract('week' from current_date)")
    Long getEarningThisWeek();

    /**
     * @return doanh thu trong ngafy
     */
    @Query("select sum(o.total)\n" +
            "from OrderEntity o\n" +
            "where o.payed = true\n" +
            "and cast(o.createdDate as date) = cast(current_date as date)")
    Long getEarningToday();

    /**
     * @return doanh thu theo thứ trong tuần hiện tại
     * @param i
     */
    @Query("select new com.ws.masterserver.dto.admin.dashboard.EarningDayDto(\n" +
            "extract('dow' from o.createdDate) - 1 as dayOfWeek,\n" +
            "sum(o.total))\n" +
            "from OrderEntity o\n" +
            "where extract('week' from o.createdDate) = extract('week' from current_date) - ?1\n" +
            "and o.payed = true\n" +
            "group by dayOfWeek\n" +
            "order by dayOfWeek")
    List<EarningDayDto> getEarningWeekWithDay(int minusWeek);
}
