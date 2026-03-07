package xenosoft.imldintelligence.common.mybatis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * JsonNode MyBatis 类型处理器，用于在数据库 JSON 字段与 Jackson JsonNode 之间转换。
 */
@MappedTypes(JsonNode.class)
@MappedJdbcTypes(JdbcType.OTHER)
public class JsonNodeTypeHandler extends BaseTypeHandler<JsonNode> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JsonNode parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.toString(), Types.OTHER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonNode getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parseJsonNode(rs.getString(columnName));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonNode getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseJsonNode(rs.getString(columnIndex));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonNode getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseJsonNode(cs.getString(columnIndex));
    }

    private JsonNode parseJsonNode(String value) throws SQLException {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readTree(value);
        } catch (JsonProcessingException e) {
            throw new SQLException("Failed to parse JSONB value", e);
        }
    }
}
