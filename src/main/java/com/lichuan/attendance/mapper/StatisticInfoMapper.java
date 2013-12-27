package com.lichuan.attendance.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.lichuan.attendance.model.PersonStatistic;

@Repository
public interface StatisticInfoMapper {
	
	@Delete("delete from statistics_info where every_month=#{every_month}")
	int clearDataByMonth(String every_month);
	
	@Select("select id,oa,user_name,department,sum(late_absolve) as late_absolve,sum(late_fine) as late_fine,sum(forget_checkin) as forget_checkin,sum(forget_fine) as forget_fine,sum(forget_checkout) as forget_checkout,sum(working_date_count) as working_date_count,sum(holidays_count) as holidays_count,sum(overtime_count) as overtime_count,sum(holiday_overtime_count) as holiday_overtime_count,sum(unknown_count) as unknown_count,sum(office_date_count) as office_date_count,sum(real_working_date_count) as real_working_date_count,sum(fanbu_date_count) as fanbu_date_count,sum(shijia_count) as shijia_count,sum(waichu_count) as waichu_count,sum(bingjia_count) as bingjia_count,sum(chuchai_count) as chuchai_count,sum(tiaoxiu_count) as tiaoxiu_count,sum(nianjia_count) as nianjia_count,sum(hunjia_count) as hunjia_count,sum(chanjia_count) as chanjia_count,sum(sangjia_count) as sangjia_count,sum(kuanggong_count) as kuanggong_count,sum(undetermined_count) as undetermined_count from statistics_info where oa in(${oaList}) and every_month>=#{start_month} and every_month<=#{end_month} group by oa order by department,user_name")
	List<PersonStatistic> fetchDatas(Map map);
	
	@Select("select sum(late_absolve) as late_absolve,sum(late_fine) as late_fine,sum(forget_checkin) as forget_checkin,sum(forget_fine) as forget_fine,sum(forget_checkout) as forget_checkout,sum(working_date_count) as working_date_count,sum(holidays_count) as holidays_count,sum(overtime_count) as overtime_count,sum(holiday_overtime_count) as holiday_overtime_count,sum(unknown_count) as unknown_count,sum(office_date_count) as office_date_count,sum(real_working_date_count) as real_working_date_count,sum(fanbu_date_count) as fanbu_date_count,sum(shijia_count) as shijia_count,sum(waichu_count) as waichu_count,sum(bingjia_count) as bingjia_count,sum(chuchai_count) as chuchai_count,sum(tiaoxiu_count) as tiaoxiu_count,sum(nianjia_count) as nianjia_count,sum(hunjia_count) as hunjia_count,sum(chanjia_count) as chanjia_count,sum(sangjia_count) as sangjia_count,sum(kuanggong_count) as kuanggong_count,sum(undetermined_count) as undetermined_count from statistics_info where oa in(${oaList}) and every_month>=#{start_month} and every_month<=#{end_month}")
	PersonStatistic fetchTotalDatas(Map map);
	
	@Insert("insert into statistics_info(sn,id,oa,user_name,every_month,department,late_absolve,late_fine,forget_checkin,forget_fine,forget_checkout,working_date_count,holidays_count,overtime_count,holiday_overtime_count,unknown_count,office_date_count,real_working_date_count,fanbu_date_count,shijia_count,waichu_count,bingjia_count,chuchai_count,tiaoxiu_count,nianjia_count,hunjia_count,chanjia_count,sangjia_count,kuanggong_count,undetermined_count,statis_time) values(null,#{id},#{oa},#{user_name},#{every_month},#{department},#{late_absolve},#{late_fine},#{forget_checkin},#{forget_fine},#{forget_checkout},#{working_date_count},#{holidays_count},#{overtime_count},#{holiday_overtime_count},#{unknown_count},#{office_date_count},#{real_working_date_count},#{fanbu_date_count},#{shijia_count},#{waichu_count},#{bingjia_count},#{chuchai_count},#{tiaoxiu_count},#{nianjia_count},#{hunjia_count},#{chanjia_count},#{sangjia_count},#{kuanggong_count},#{undetermined_count},now())")
	int save(PersonStatistic statistic);
}
