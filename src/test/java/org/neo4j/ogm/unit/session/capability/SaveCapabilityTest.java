/*
 * Copyright (c) 2002-2015 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This product is licensed to you under the Apache License, Version 2.0 (the "License").
 * You may not use this product except in compliance with the License.
 *
 * This product may include a number of subcomponents with
 * separate copyright notices and license terms. Your use of the source
 * code for these subcomponents is subject to the terms and
 * conditions of the subcomponent's license, as noted in the LICENSE file.
 *
 */

package org.neo4j.ogm.unit.session.capability;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.neo4j.ogm.domain.music.Artist;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.testutil.Neo4jIntegrationTestRule;

/**
 * @author Luanne Misquitta
 */
public class SaveCapabilityTest {

	@ClassRule
	public static Neo4jIntegrationTestRule databaseServerRule = new Neo4jIntegrationTestRule();

	private Session session;
	private Artist aerosmith;
	private Artist bonJovi;
	private Artist defLeppard;

	@Before
	public void init() throws IOException {
		SessionFactory sessionFactory = new SessionFactory("org.neo4j.ogm.domain.music");
		session = sessionFactory.openSession(databaseServerRule.url());
		aerosmith = new Artist("Aerosmith");
		bonJovi = new Artist("Bon Jovi");
		defLeppard = new Artist("Def Leppard");
	}

	@After
	public void clearDatabase() {
		databaseServerRule.clearDatabase();
	}

	/**
	 * @see Issue #84
	 */
	@Test
	public void saveCollectionShouldSaveLists() {
		List<Artist> artists = Arrays.asList(aerosmith, bonJovi, defLeppard);
		session.save(artists);
		session.clear();
		assertEquals(3, session.countEntitiesOfType(Artist.class));
	}

	/**
	 * @see Issue #84
	 */
	@Test
	public void saveCollectionShouldSaveSets() {
		Set<Artist> artists = new HashSet<>();
		artists.add(aerosmith);
		artists.add(bonJovi);
		artists.add(defLeppard);
		session.save(artists);
		session.clear();
		assertEquals(3, session.countEntitiesOfType(Artist.class));
	}


	/**
	 * @see Issue #84
	 */
	@Test
	public void saveCollectionShouldSaveArrays() {
		Artist[] artists = new Artist[] {aerosmith, bonJovi, defLeppard};
		session.save(artists);
		session.clear();
		assertEquals(3, session.countEntitiesOfType(Artist.class));
	}
}
