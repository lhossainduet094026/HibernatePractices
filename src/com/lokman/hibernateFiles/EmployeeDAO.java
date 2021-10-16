package com.lokman.hibernateFiles;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class EmployeeDAO {
	// private static SessionFactory sessionFactory;
	private Session currentSession;
	private Transaction currentTransaction;
	private static EmployeeDAO employeeDAO = new EmployeeDAO();

	private EmployeeDAO() {

	}

	public static EmployeeDAO getInstance() {
		return employeeDAO;// followed singleton design pattern
	}

	private static SessionFactory getSessionFactory() {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		return sessionFactory;
	}

	private Session getCurrentSession() {
		currentSession = getSessionFactory().openSession();
		return currentSession;
	}

	private Session openCurrentSessionWithTransaction() {
		currentSession = getCurrentSession();
		this.currentTransaction = (Transaction) currentSession.beginTransaction();
		return currentSession;
	}

	private void closeCurrentSession() {
		getCurrentSession().close();
	}

	public void closeCurrentSessionwithTransaction() throws SecurityException, RollbackException,
			HeuristicMixedException, HeuristicRollbackException, SystemException {
		currentTransaction.commit();
		closeCurrentSession();
	}

	// save employee
	public Integer save(Employee employee) {
		try {
			openCurrentSessionWithTransaction().save(employee);
			closeCurrentSessionwithTransaction();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return employee.getId();
	}
	// list employees
	// update employee
	// delete employee
}
