package com.lokman.hibernateFiles;

import java.util.List;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class EmployeeDAO {
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
	public List<Employee> findAll() {
		try {
			Criteria cr = openCurrentSessionWithTransaction().createCriteria(Employee.class, null);
			List<Employee> employeeList = cr.list();
			closeCurrentSessionwithTransaction();
			return employeeList;

		} catch (Exception ex) {

		}
		return null;

	}

	// delete employee

	public void delete(Employee employee) {
		try {
			openCurrentSessionWithTransaction().delete(employee);
			closeCurrentSessionwithTransaction();
		} catch (Exception ex) {

		}

	}

	public Employee findById(int id) {
		try {
			Criteria cr = openCurrentSessionWithTransaction().createCriteria(Employee.class);
			cr.add(Restrictions.eq("id", id));
			List list = cr.list();
			closeCurrentSessionwithTransaction();
			return (Employee) list.get(0);
		} catch (Exception ex) {

		}
		return null;
	}

	public Long sumOfSalaries() {
		try {
			Criteria cr = openCurrentSessionWithTransaction().createCriteria(Employee.class);
			cr.setProjection(Projections.sum("salary"));
			Long totalSalary = (Long) cr.list().get(0);
			closeCurrentSessionwithTransaction();
			return totalSalary;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return Long.MIN_VALUE;
	}

	// method to read all the employee using scalar query

	public List getAllEmployee() {
		String sql = "select first_name,salary from Employee";
		SQLQuery query = openCurrentSessionWithTransaction().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		try {
			closeCurrentSessionwithTransaction();
		} catch (SecurityException | RollbackException | HeuristicMixedException | HeuristicRollbackException
				| SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List list = query.list();
		return list;

	}

}
