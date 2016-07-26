package dao;

import entity.socialDynamics;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2016/7/23.
 */
public interface DynamicsDao {

    /**
     * ����̬�������ݿ�
     * @param userId
     * @param dynamicsText
     * @param dynamicsFile
     * @return
     */
    int insertDynamics(@Param("userId") int userId,
                       @Param("dynamicsText") String dynamicsText,
                       @Param("dynamicsFile") String dynamicsFile);


    /**
     * �������ݿ����ȫ�����˶�̬
     * @return
     */
    List<socialDynamics> selectAllDynamics();

    /**
     * ���ҵ�ǰҳ�涯̬���������ֵ
     * @return
     */
    String selectMaxDynamicsId();

    /**
     * ���ұȲ���dynamicsId��Ķ�̬
     * @param dynamicsId
     * @return
     */
    List<socialDynamics> selectDynamicsFromPos(@Param("dynamicsId") int dynamicsId);

}
