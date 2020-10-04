package dao;

import hibernate.util.HibernateUtil;

import javax.persistence.EntityManager;

public class AbstractAppUserDao {

    protected final HibernateUtil hibernateUtil = HibernateUtil.getInstance();
    protected final EntityManager em = hibernateUtil.getEm();

}
