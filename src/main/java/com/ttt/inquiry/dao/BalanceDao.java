package com.ttt.inquiry.dao;

import java.math.BigDecimal;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import com.ttt.inquiry.dao.mappers.BalanceMapper;
import com.ttt.inquiry.domain.Balance;

public interface BalanceDao {
	@Mapper(BalanceMapper.class)
	@SqlQuery("select * from balance where userName = :userName")
	Balance getBalanceByUserName(@Bind("userName") String userName);
	
	@GetGeneratedKeys
	@SqlUpdate("insert into balance (id, userName, balance, currency) values (NULL, :userName, :balance, :currency)")
	long createBalance(@Bind("userName") String userName, @Bind("balance") BigDecimal balance, @Bind("currency") String currency);
	
	@SqlUpdate("update balance set balance = :balance, currency = :currency where userName = :userName")
	void updateBalanceByUserName(@Bind("userName") String userName, @Bind("balance") BigDecimal balance, @Bind("currency") String currency);
	
	@SqlUpdate("delete from balance where userName = :userName")
	void deleteBalanceByUserName(@Bind("userName") String userName);
}
