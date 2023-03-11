package com.herewego.herewegoapi.common;

import com.herewego.herewegoapi.exceptions.ErrorCode;
import com.herewego.herewegoapi.exceptions.ForwardException;
import com.herewego.herewegoapi.model.request.ChangeGameUnitDTO;
import com.herewego.herewegoapi.service.UserService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.List;

@Component
public class GameUnitValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @SneakyThrows
    @Override
    public void validate(Object target, Errors errors) {
        ChangeGameUnitDTO changeGameUnitDTO = (ChangeGameUnitDTO) target;

        validatetring(changeGameUnitDTO.getAsis());
        validatetring(changeGameUnitDTO.getTobe());
    }

    private void validatetring(String gameUnit) throws ForwardException {
        List<String> unitList = Arrays.asList(gameUnit.split(","));
        for (String s : unitList) {
            Integer.parseInt(s);
        }

    }
}
