package ad.hibenate.com.Test;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import ad.hibernate.com.UserEJB;

public class UserEJBTest {
	public static UserEJB ejb = new UserEJB();
	private static Logger log = Logger.getLogger(UserEJBTest.class);

	public static void main(String[] args) throws Exception {
		// testAdd();
		// testGet();
		// testUpdate();
		// testDelete();
		// testAuthenticate();
		// testlistRecord();
		// testLike();
		// testGetColumns();
		// testCriteria();
		// testSeletedCriteria();// PROJECTION
		// testAggreagteCriteria();
		// testRestrictionAND();
		// testRestrictionOR();
		// testCriteriaOrderBy();

	}

	private static void testCriteriaOrderBy() {
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session s = sessionFactory.openSession();
		Criteria ct = s.createCriteria(UserEJB.class);
		/* ct.addOrder(Order.asc("id")); */
		ct.addOrder(Order.desc("id"));
		/* ct.addOrder(Order.desc("firstName")); */
		List list = ct.list();
		Iterator it = list.iterator();
		System.out
				.println("ID \tFIRST_NAME LAST_NAME \tCITY\tUSER_ID PASSCODE");
		while (it.hasNext()) {
			ejb = (UserEJB) it.next();
			System.out.print("\n" + ejb.getId());
			System.out.print("\t" + ejb.getFirstName());
			System.out.print("\t" + ejb.getLastName());
			System.out.print("\t" + ejb.getCity());
			System.out.print("\t" + ejb.getUserid());
			System.out.print("\t" + ejb.getPasscode());
		}

	}

	private static void testRestrictionOR() {

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session s = sessionFactory.openSession();
		Criteria ct = s.createCriteria(UserEJB.class);// TAKE REFERENCE OF CLASS
														// (.getclass)

		/********** CRITERIAS ARE APPLIED ONLY ON CLASS **************/

		Criteria ctt = ct
				.add(Restrictions.or(Restrictions.like("firstName", "V_%"),
						Restrictions.eq("id", 2)));// OR CONDITION ONLY ONE
													// SHOULD BE
													// TRUE (LHS , RHS)
		if (ctt == null) {
			System.out.println("NO RECORD FOUND");

		}
		List list = ct.list();
		Iterator it = list.iterator();
		System.out.println("ID FIRST_NAME\tLAST_NAME\tCITY\tUSER_ID PASSCODE");
		while (it.hasNext()) {
			ejb = (UserEJB) it.next();
			System.out.print("\n" + ejb.getId());
			System.out.print("\t" + ejb.getFirstName());
			System.out.print("\t" + ejb.getLastName());
			System.out.print("\t" + ejb.getCity());
			System.out.print("\t" + ejb.getUserid());
			System.out.print("\t" + ejb.getPasscode());

		}

	}

	private static void testRestrictionAND() {
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session s = sessionFactory.openSession();
		Criteria ct = s.createCriteria(UserEJB.class);

		ct.add(Restrictions.and(Restrictions.like("firstName", "V_%"),
				Restrictions.eq("id", 11)));
		/* ct.add(Restrictions.like("city", "N%")); */// AND CONDITION BOTH
														// PARTS
		// SHOULD BE TRUE
		/* ct.add(Restrictions.eq("id", 3)); */

		List list = ct.list();
		if (list.isEmpty()) {
			System.out.println("\n\t" + "NO RECORD FOUND");

		} else {
			Iterator it = list.iterator();
			System.out
					.println("ID\tFIRST_NAME LAST_NAME PASSCODE USER_ID CITY");
			while (it.hasNext()) {
				ejb = (UserEJB) it.next();
				System.out.print("\n" + ejb.getId());
				System.out.print("\t" + ejb.getFirstName());
				System.out.print("\t" + ejb.getLastName());
				System.out.print("\t" + ejb.getPasscode());
				System.out.print("\t" + ejb.getUserid());
				System.out.print("\t" + ejb.getCity());
			}
		}
	}

	/* PROJECTION AGGREGATE FUNCTION */

	private static void testAggreagteCriteria() {

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session s = sessionFactory.openSession();
		Criteria ct = s.createCriteria(UserEJB.class);
		ProjectionList p = Projections.projectionList();
		p.add(Projections.count("id"));
		p.add(Projections.rowCount());
		/* p.add(Projections.groupProperty("city")); */
		ct.setProjection(p);
		List list = ct.list();
		Iterator it = list.iterator();
		Object coloumns[];
		System.out.println("COUNT\tROWCOUNT\tCITY");
		while (it.hasNext()) {
			coloumns = (Object[]) it.next();
			int count = (int) coloumns[0];
			int rowcount = (int) coloumns[1];
			/* String city = (String) coloumns[2]; */
			System.out.print("\n" + count);
			System.out.print("\t" + rowcount);
			/* System.out.print("\t" + city); */
		}

	}

	/********************************* CRITERIA ************************************************/

	/********************** PROJECTION ***********************************/

	/********************** FOR GETING PARTIAL OBJECT ************************/

	/********* SELECT ID,FIRST_NAME,LAST_NAME FROM USERS *******************/

	private static void testSeletedCriteria() {
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session s = sessionFactory.openSession();
		Criteria ct = s.createCriteria(UserEJB.class);// SELECT * FROM USERS

		ProjectionList p = Projections.projectionList();// PROJECTION LIST
		p.add(Projections.property("id"));// ADDING PROJECTION TO PROJECTION
											// LIST
		p.add(Projections.property("firstName"));
		p.add(Projections.property("lastName"));
		ct.setProjection(p);// SETTING PROJECTION TO CRITERIA
		List rows = ct.list();// LIST INTERFACE
		Iterator it = rows.iterator();
		Object Columns[];// OBJECT ARRAY
		System.out.println("ID FIRST_NAME LAST_NAME");
		while (it.hasNext()) {
			Columns = (Object[]) it.next();
			int id = (int) Columns[0];
			String firstName = (String) Columns[1];
			String lastName = (String) Columns[2];
			System.out.print("\n" + id);
			System.out.print("\t" + firstName);
			System.out.print("\t" + lastName);

		}
		s.close();
		/* sessionFactory.close(); */
	}

	/********************************** CRITERIA **********************************************/
	/****************** GET ALL RECORDS ************************************/
	/******************** SELECT * FROM USERS ******************************/

	private static void testCriteria() {
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session s = sessionFactory.openSession();
		/* Query q=s.createQuery("from UserEJB"); */
		Criteria ct = s.createCriteria(UserEJB.class);// SELECT * FROM USERS
		List list = ct.list();
		Iterator it = list.iterator();
		System.out.println("ID FIRST_NAME LAST_NAME\tCITY\tUSER_ID\tPASSCODE");
		while (it.hasNext()) {
			ejb = (UserEJB) it.next();
			System.out.print("\n" + ejb.getId());
			System.out.print("\t" + ejb.getFirstName());
			System.out.print("\t" + ejb.getLastName());
			System.out.print("\t" + ejb.getCity());
			System.out.print("\t" + ejb.getUserid());
			System.out.print("\t" + ejb.getPasscode());

		}

		s.close();
	}

	/************************************* GET COLUMNS *****************************************/
	/****************************** RETURNS PARTIAL OBJECT ***************/

	/************* SELECT ID,FIRST_NAME,LAST_NAME FROM USERS *************/

	private static void testGetColumns() {
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session s = sessionFactory.openSession();
		Query q = s.createQuery("select id,firstName,lastName from UserEJB ");
		List rows = q.list();
		Iterator it = rows.iterator();

		Object[] columns;// OBJECT CLASS
		System.out.println("ID FIRST_NAME LAST_NAME");
		while (it.hasNext()) {
			columns = (Object[]) it.next();// RETURNS PARTIAL OBJECT
			int id = (int) columns[0];// ID
			String firstName = (String) columns[1];// FIRST_NAME
			String lastName = (String) columns[2];// LAST_NAME
			System.out.print("\n" + id);
			System.out.print("\t" + firstName);
			System.out.print("\t" + lastName);

		}
		s.close();

	}

	/**************************** LIKE OPERATOR *********************************************/

	/**************** SELECT * FROM USERS WHERE FIRST_NAME LIKE 'R%' *********************/

	private static void testLike() {
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session s = sessionFactory.openSession();
		Query q = s.createQuery("from UserEJB where firstName like 'R%' ");
		List list = q.list();
		Iterator it = list.iterator();
		System.out.println("ID FIRST_NAME LAST_NAME\tCITY\tUSER_ID\tPASSCODE");
		while (it.hasNext()) {
			ejb = (UserEJB) it.next();
			System.out.print("\n" + ejb.getId());
			System.out.print("\t" + ejb.getFirstName());
			System.out.print("\t" + ejb.getLastName());
			System.out.print("\t" + ejb.getCity());
			System.out.print("\t" + ejb.getUserid());
			System.out.print("\t" + ejb.getPasscode());

		}
		s.close();

	}

	/****************************
	 * LIST RECORD
	 * 
	 * @throws SQLException
	 ***********************************************/

	/******************** SELECT * FROM USERS ***************************/

	private static void testlistRecord() throws SQLException {
		log.debug("USER LIST STARTED");
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session s = sessionFactory.openSession();
		/* org.hibernate.Query q=s.createSQLQuery("SELECT * FROM users"); */
		org.hibernate.Query q = s.createQuery("from UserEJB");
		/* SQLQuery rs=s.createSQLQuery("SELECT * FROM users"); */
		List list = q.list();
		Iterator it = list.iterator();

		System.out.println("ID FIRST_NAME LAST_NAME\tCITY\tUSER_ID\tPASSCODE");
		while (it.hasNext()) {

			ejb = (UserEJB) it.next();
			System.out.print("\n" + ejb.getId());
			System.out.print("\t" + ejb.getFirstName());
			System.out.print("\t" + ejb.getLastName());
			System.out.print("\t" + ejb.getCity());
			System.out.print("\t" + ejb.getUserid());
			System.out.print("\t" + ejb.getPasscode());

		}
		s.close();
	}

	/********************************** AUTHENTICATE ******************************************/

	/*********** SELECT * FROM USERS WHERE USER_ID=? AND PASSCODE=? **********************/

	private static void testAuthenticate() {
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session s = sessionFactory.openSession();

		ejb.setUserid("Vibhour@gmail.com");
		ejb.setPasscode("me22@Bean");

		// ******************CUSTOM QUERY*********************************//
		org.hibernate.Query q = s
				.createQuery("from UserEJB where userid=? and passcode=?");
		q.setString(0, ejb.getUserid());
		q.setString(1, ejb.getPasscode());

		List list = q.list();
		if (list.size() == 2) {
			ejb = (UserEJB) list.get(0);
			System.out.println("SUCCESSFULLY LOGIN");
		} else {
			System.out.println("USER NOT FOUND");
		}

		s.close();

	}

	/**************************************** DELETE *****************************************/

	/***************** DELETE FROM USERS WHERE ID=4 *************************************/

	private static void testDelete() {
		ejb.setId(4);
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session s = sessionFactory.openSession();
		Transaction tx = s.beginTransaction();
		s.delete(ejb);// FOR DELETE
		System.out.println("RECORD DELETED");
		tx.commit();
		s.close();

	}

	/*************************************** UPDATE ****************************************/

	/****
	 * UPDATE USERS SET FIRST_NAME=?,LAST_NAME=?,CITY=?,USER_ID=?,PASSCODE=?
	 * WHERE ID=?
	 ***/

	private static void testUpdate() {
		ejb.setId(3);
		ejb.setFirstName("ROBIN");
		ejb.setLastName("GILL");
		ejb.setCity("NEW YORK");
		ejb.setUserid("Robin@gmail.com");
		ejb.setPasscode("2020");

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session s = sessionFactory.openSession();
		Transaction tx = s.beginTransaction();
		s.update(ejb);// FOR UPDATE
		System.out.println("UPDATED");
		tx.commit();
		s.close();

	}

	/************************************ GET **************************************************/

	/*************************** SELECT * FROM USERS WHERE ID=? ********************************/

	private static void testGet() {

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session s = sessionFactory.openSession();// create session

		System.out.println("ID FIRST_NAME LAST_NAME\tCITY\tUSER_ID\tPASSCODE");
		UserEJB ejb = (UserEJB) s.get(UserEJB.class, 6);// FOR GETTING SPECIFIC
														// RECORD
		System.out.print(ejb.getId());
		System.out.print("\t" + ejb.getFirstName());
		System.out.print("\t" + ejb.getLastName());
		System.out.print("\t" + ejb.getCity());
		System.out.print("\t" + ejb.getUserid());
		System.out.print("\t" + ejb.getPasscode());

		s.close();
	}

	/********************************** ADD **************************************************/

	/***************** INSERT INTO USERS VALUES(?,?,?,?,?) *******************************************/

	private static void testAdd() {
		ejb.setFirstName("Rajiv");
		ejb.setLastName("Bansal");
		ejb.setCity("INDORE");
		ejb.setUserid("bansal@gmail.com");
		ejb.setPasscode("me22@Bean");

		SessionFactory sessionfactory = new Configuration().configure()
				.buildSessionFactory();
		Session s = sessionfactory.openSession();
		Transaction tx = s.beginTransaction();
		s.save(ejb);// FOR ADDING
		tx.commit();
		if (s != null) {
			s.close();
		}
	}
}