/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import enitity.FishStick;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jennifer
 */
@Stateless
public class FishStickFacade extends AbstractFacade<FishStick> implements FishStickFacadeRemote {

    @PersistenceContext(unitName = "CST8277Assignment4-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FishStickFacade() {
        super(FishStick.class);
    }
    
}
