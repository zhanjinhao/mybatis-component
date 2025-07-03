package cn.addenda.component.mybatis.typehandler;

import cn.addenda.component.base.datetime.DateUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * 时间戳 《--》 LocalDateTime
 *
 * @author addenda
 * @since 2022/2/5 12:33
 */
@MappedJdbcTypes(value = JdbcType.BIGINT)
public class TsLocalDateTimeHandler extends BaseTypeHandler<LocalDateTime> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws SQLException {
    if (parameter == null) {
      ps.setLong(i, 0);
    } else {
      ps.setLong(i, DateUtils.localDateTimeToTimestamp(parameter));
    }
  }

  @Override
  public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
    long time = rs.getLong(columnName);
    return covertToLocalDateTime(time);
  }

  @Override
  public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    long aLong = rs.getLong(columnIndex);
    return covertToLocalDateTime(aLong);
  }

  @Override
  public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    long aLong = cs.getLong(columnIndex);
    return covertToLocalDateTime(aLong);
  }

  private LocalDateTime covertToLocalDateTime(long time) {
    if (time == 0L) {
      return null;
    }
    Instant instant = Instant.ofEpochMilli(time);
    return DateUtils.timestampToLocalDateTime(instant.toEpochMilli());
  }

}
