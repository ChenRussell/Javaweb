package dao;

import entity.SocialDynamics;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Administrator on 2016/7/23.
 */
public interface DynamicsDao {

    /**
     * 将动态插入数据库
     * @param userId
     * @param dynamicsText
     * @param dynamicsFile
     * @return
     */
    int insertDynamics(@Param("userId") int userId,
                       @Param("dynamicsText") String dynamicsText,
                       @Param("dynamicsFile") String dynamicsFile,
                       @Param("createTime") Timestamp createTime);


    /**
     * 查找数据库表中全部个人动态
     * @return
     */
    List<SocialDynamics> selectAllDynamics();

    /**
     * 查找当前页面动态主键的最大值
     * @return
     */
    String selectMaxDynamicsId();

    /**
     * 查找比参数dynamicsId大的动态
     * @param dynamicsId
     * @return
     */
    List<SocialDynamics> selectDynamicsFromPos(@Param("dynamicsId") int dynamicsId);

}
