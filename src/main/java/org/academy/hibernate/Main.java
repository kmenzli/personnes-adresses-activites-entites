package org.academy.hibernate;

import org.academy.hibernate.entities.Activite;
import org.academy.hibernate.entities.Adresse;
import org.academy.hibernate.entities.Personne;
import org.academy.hibernate.entities.PersonneActivite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

@SuppressWarnings( { "unused", "unchecked" })
public class Main {

	// constantes
	private final static String TABLE_PERSONNE_ACTIVITE = "jpa07_hb_personne_activite";

	private final static String TABLE_PERSONNE = "jpa07_hb_personne";

	private final static String TABLE_ACTIVITE = "jpa07_hb_activite";

	private final static String TABLE_ADRESSE = "jpa07_hb_adresse";

	// Contexte de persistance
	private static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("jpa");

	private static EntityManager em = null;

	// objets partag�s
	private static Personne p1, p2, p3;

	private static Adresse adr1, adr2, adr3, adr4;

	private static Activite act1, act2, act3;

	private static PersonneActivite p1act1, p1act2, p2act1, p2act3;

	public static void main(String[] args) throws Exception {
		// nettoyage base
		log("clean");
		clean();

		// dumps
		dumpPersonne();
		dumpAdresse();
		dumpActivite();
		dumpPersonne_Activite();

		// test1
		log("test1");
		test1();

		// test2
		log("test2");
		test2();

		// test3
		log("test3");
		test3();

		// test4
		log("test4");
		test4();

		// test5
		log("test5");
		test5();

		// fin contexte de persistance
		if (em != null && em.isOpen())
			em.close();

		// fermeture EntityManagerFactory
		emf.close();
	}

	// r�cup�rer l'EntityManager courant
	private static EntityManager getEntityManager() {
		if (em == null || !em.isOpen()) {
			em = emf.createEntityManager();
		}
		return em;
	}

	// r�cup�rer un EntityManager neuf
	private static EntityManager getNewEntityManager() {
		if (em != null && em.isOpen()) {
			em.close();
		}
		em = emf.createEntityManager();
		return em;
	}

	// affichage contenu table Personne
	private static void dumpPersonne() {
		// contexte de persistance
		EntityManager em = getEntityManager();
		// d�but transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// affichage personnes
		System.out.println("[personnes]");
		for (Object p : em.createQuery(
				"select p from Personne p order by p.nom asc").getResultList()) {
			System.out.println(p);
		}
		// fin transaction
		tx.commit();
	}

	// affichage contenu table Adresse
	private static void dumpAdresse() {
		// contexte de persistance
		EntityManager em = getEntityManager();
		// d�but transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// affichage adresses
		System.out.println("[adresses]");
		for (Object a : em.createQuery("select a from Adresse a").getResultList()) {
			System.out.println(a);
		}
		// fin transaction
		tx.commit();
	}

	// affichage contenu table Activite
	private static void dumpActivite() {
		// contexte de persistance
		EntityManager em = getEntityManager();
		// d�but transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// affichage activit�s
		System.out.println("[activites]");
		for (Object a : em.createQuery("select a from Activite a").getResultList()) {
			System.out.println(a);
		}
		// fin transaction
		tx.commit();
	}

	// affichage contenu table Personne_Activite
	private static void dumpPersonne_Activite() {
		// contexte de persistance
		EntityManager em = getEntityManager();
		// d�but transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// affichage personnes/activit�s
		System.out.println("[personnes/activites]");
		for (Object pa : em.createQuery("select pa from PersonneActivite pa")
				.getResultList()) {
			System.out.println(pa);
		}
		// fin transaction
		tx.commit();
	}

	// raz BD
	private static void clean() {
		// contexte de persistance
		EntityManager em = getEntityManager();
		// d�but transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// requ�te
		Query sql1;
		// supprimer les �l�ments de la table PERSONNE_ACTIVITE
		sql1 = em.createNativeQuery("delete from " + TABLE_PERSONNE_ACTIVITE);
		sql1.executeUpdate();
		// supprimer les �l�ments de la table PERSONNE
		sql1 = em.createNativeQuery("delete from " + TABLE_PERSONNE);
		sql1.executeUpdate();
		// supprimer les �l�ments de la table ACTIVITE
		sql1 = em.createNativeQuery("delete from " + TABLE_ACTIVITE);
		sql1.executeUpdate();
		// supprimer les �l�ments de la table ADRESSE
		sql1 = em.createNativeQuery("delete from " + TABLE_ADRESSE);
		sql1.executeUpdate();
		// fin transaction
		tx.commit();
	}

	// logs
	private static void log(String message) {
		System.out.println("main : ----------- " + message);
	}

	// cr�ation d'objets
	public static void test1() throws ParseException {
		// contexte de persistance
		EntityManager em = getEntityManager();
		// cr�ation personnes
		p1 = new Personne("p1", "Paul", new SimpleDateFormat("dd/MM/yy")
				.parse("31/01/2000"), true, 2);
		p2 = new Personne("p2", "Sylvie", new SimpleDateFormat("dd/MM/yy")
				.parse("05/07/2001"), false, 0);
		p3 = new Personne("p3", "Sylvie", new SimpleDateFormat("dd/MM/yy")
				.parse("05/07/2001"), false, 0);
		// cr�ation adresses
		adr1 = new Adresse("adr1", null, null, "49000", "Angers", null, "France");
		adr2 = new Adresse("adr2", "Les Mimosas", "15 av Foch", "49002", "Angers",
				"03", "France");
		adr3 = new Adresse("adr3", "x", "x", "x", "x", "x", "x");
		adr4 = new Adresse("adr4", "y", "y", "y", "y", "y", "y");
		// cr�ation activites
		act1 = new Activite();
		act1.setNom("act1");
		act2 = new Activite();
		act2.setNom("act2");
		act3 = new Activite();
		act3.setNom("act3");
		// associations personne <--> adresse
		p1.setAdresse(adr1);
		adr1.setPersonne(p1);
		p2.setAdresse(adr2);
		adr2.setPersonne(p2);
		p3.setAdresse(adr3);
		adr3.setPersonne(p3);
		// d�but transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// persistance des personnes
		em.persist(p1);
		em.persist(p2);
		em.persist(p3);
		// et de l'adresse a4 non li�e � une personne
		em.persist(adr4);
		// persistance activites
		em.persist(act1);
		em.persist(act2);
		em.persist(act3);
		// fin transaction 1
		tx.commit();
		// d�but transaction 2
		tx = em.getTransaction();
		tx.begin();
		// associations personne <-->activite
		p1act1 = new PersonneActivite(p1, act1);
		p1act2 = new PersonneActivite(p1, act2);
		p2act1 = new PersonneActivite(p2, act1);
		p2act3 = new PersonneActivite(p2, act3);
		// on persiste ces associations
		em.persist(p1act1);
		em.persist(p1act2);
		em.persist(p2act1);
		em.persist(p2act3);
		// fin transaction 2
		tx.commit();
		// dumps
		dumpPersonne();
		dumpActivite();
		dumpAdresse();
		dumpPersonne_Activite();
	}

	// suppression Personne p1
	public static void test2() {
		// contexte de persistance
		EntityManager em = getEntityManager();
		// d�but transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// suppression d�pendances sur p1 : pas n�cessaire � hibernate mais
		// indispensable � toplink
		act1.getPersonnes().remove(p1act1);
		act2.getPersonnes().remove(p1act2);
		// suppression personne p1
		em.remove(p1);
		// fin transaction
		tx.commit();
		// on affiche les nouvelles tables
		dumpPersonne();
		dumpActivite();
		dumpAdresse();
		dumpPersonne_Activite();
	}

	// suppression activite act1
	public static void test3() {
		// contexte de persistance
		EntityManager em = getEntityManager();
		// d�but transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// suppression d�pendances sur act1 : pas n�cessaire � hibernate mais
		// indispensable � toplink
		p2.getActivites().remove(p2act1);
		// suppression activit� act1
		em.remove(act1);
		// fin transaction
		tx.commit();
		// on affiche les nouvelles tables
		dumpPersonne();
		dumpActivite();
		dumpAdresse();
		dumpPersonne_Activite();
	}

	// r�cup�ration activit�s d'une personne
	public static void test4() {
		// contexte de persistance
		EntityManager em = getNewEntityManager();
		// d�but transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// on r�cup�re la personne p2
		p2 = em.find(Personne.class, p2.getId());
		System.out.format("1 - Activit�s de la personne p2 (JPQL) :%n");
		// on scanne ses activit�s
		for (Object pa : em
				.createQuery(
						"select a.nom from Activite a join a.personnes pa where pa.personne.nom='p2'")
				.getResultList()) {
			System.out.println(pa);
		}
		// on passe par la relation inverse de p2
		p2 = em.find(Personne.class, p2.getId());
		System.out.format("2 - Activit�s de la personne p2 (relation inverse) :%n");
		// on scanne ses activit�s
		for (PersonneActivite pa : p2.getActivites()) {
			System.out.println(pa.getActivite().getNom());
		}
		// fin transaction
		tx.commit();
	}

	// r�cup�ration personnes faisant une activit� donn�e
	public static void test5() {
		// contexte de persistance
		EntityManager em = getNewEntityManager();
		// d�but transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		System.out.format("1 - Personnes pratiquant l'activit� act3 (JPQL) :%n");
		// on demande les activit�s de p2
		for (Object pa : em
				.createQuery(
						"select p.nom from Personne p join p.activites pa where pa.activite.nom='act3'")
				.getResultList()) {
			System.out.println(pa);
		}
		// on passe par la relation inverse de act3
		System.out
				.format("2 - Personnes pratiquant l'activit� act3 (relation inverse) :%n");
		act3 = em.find(Activite.class, act3.getId());
		for (PersonneActivite pa : act3.getPersonnes()) {
			System.out.println(pa.getPersonne().getNom());
		}
		// fin transaction
		tx.commit();
	}

}