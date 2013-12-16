package com.hskj.salary.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hskj.salary.model.Salary;

public interface SalaryDAO {

	List<Salary> queryListByMonth(@Param(value ="every_month") String everyMonth);

}
