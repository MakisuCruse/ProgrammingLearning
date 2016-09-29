package spittr.service;

import spittr.model.Spitter;

/**
 * Created by makisucruse on 16/9/29.
 */
public interface ISpitterService {
    Spitter createSpitter(String email, String username, String password);

    Spitter createSpitter(Spitter spitter);

    Spitter getSpitterByEmail(String email);

    Spitter login(Spitter spitter);

    Spitter login(String email, String password);
}
