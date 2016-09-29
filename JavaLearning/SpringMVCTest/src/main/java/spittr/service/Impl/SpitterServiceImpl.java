package spittr.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import spittr.util.DigestUtils;
import spittr.mapper.SpitterMapper;
import spittr.model.Spitter;
import spittr.service.ISpitterService;

/**
 * Created by makisucruse on 16/9/29.
 */
public class SpitterServiceImpl implements ISpitterService {
    @Autowired
    private SpitterMapper mapper;

    @Override
    public Spitter createSpitter(String email, String username, String password) {
        Spitter spitter = new Spitter(email, username, DigestUtils.md5Hex(password));
        if (isExistedSpitter(spitter)) {
            //处理异常
        }
        mapper.createSpitter(spitter);
        return getSpitterByEmail(email);
    }

    @Override
    public Spitter createSpitter(Spitter spitter) {
        return createSpitter(spitter.getEmail(), spitter.getUsername(), spitter.getPassword());
    }

    @Override
    public Spitter getSpitterByEmail(String email) {
        return mapper.findSpitterByEmail(email);
    }

    @Override
    public Spitter login(Spitter spitter) {
        return login(spitter.getEmail(), spitter.getPassword());
    }

    @Override
    public Spitter login(String email, String password) {
        Spitter spitter = mapper.findSpitterByEmail(email);
        String passwordHash = DigestUtils.md5Hex(password);
        if (spitter == null || !spitter.getPassword().equals(DigestUtils.md5Hex(password))) {
            //抛出异常
        }
        return spitter;
    }

    private Boolean isExistedSpitter(Spitter spitter) {
        return mapper.findSpitterByEmail(spitter.getEmail()) != null;
    }
}
