/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import enitity.FishStick;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Jennifer
 */
@Remote
public interface FishStickFacadeRemote {

    void create(FishStick fishStick);

    void edit(FishStick fishStick);

    void remove(FishStick fishStick);

    FishStick find(Object id);

    List<FishStick> findAll();

    List<FishStick> findRange(int[] range);

    int count();
    
}
