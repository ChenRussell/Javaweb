package org.seckill.dao;


import org.seckill.entity.SuccessKilled;

/**
 * Created by Administrator on 2016/5/23.
 */
public interface SuccessKilledDao {

    /**
     * ���빺����ϸ�����������ɹ����ظ�
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(int seckillId,String userPhone);

    /**
     * ����id��ѯSuccessKilled��Я����ɱ��Ʒ����ʵ��
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(int seckillId);
}
