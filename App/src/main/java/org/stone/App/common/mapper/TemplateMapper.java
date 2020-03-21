package org.stone.app.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.stone.app.test.po.Student;

/**
 * 
 * @author wu_firefox@163.com
 *
 */
@Mapper
public interface TemplateMapper {

	@Select("select * from tb_user")
	public List<Student> getAll();

}
