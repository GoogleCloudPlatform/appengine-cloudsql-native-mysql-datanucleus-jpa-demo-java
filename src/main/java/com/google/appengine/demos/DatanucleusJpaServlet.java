/**
 * Copyright 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.appengine.demos;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.http.*;

public class DatanucleusJpaServlet extends HttpServlet {
  public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws IOException {
    res.setContentType("text/plain");

    EntityManager em = EMF.get().createEntityManager();

    // Insert a few rows.
    em.getTransaction().begin();
    em.persist(new Greeting("user", new Date(), "Hello!"));
    em.persist(new Greeting("user", new Date(), "Hi!"));
    em.getTransaction().commit();
    em.close();

    // List all the rows.
    em = EMF.get().createEntityManager();
    em.getTransaction().begin();
    List<Greeting> result = em
        .createQuery("SELECT g FROM Greeting g")
        .getResultList();
    for (Greeting g : result) {
      res.getWriter().println(
          g.getId() + " " +
          g.getAuthor() + "(" + g.getDate() + "): " +
          g.getContent());
    }
    em.getTransaction().commit();
    em.close();

  }
}
