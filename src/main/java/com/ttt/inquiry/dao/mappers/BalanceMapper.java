package com.ttt.inquiry.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.ttt.inquiry.domain.Balance;

public class BalanceMapper implements ResultSetMapper<Balance>{

	@Override
	public Balance map(int index, ResultSet rs, StatementContext ctx)
			throws SQLException {
		return new Balance(rs.getLong("id"),
				rs.getString("userName"),
				rs.getBigDecimal("balance"), 
				rs.getString("currency"));
	}

}
