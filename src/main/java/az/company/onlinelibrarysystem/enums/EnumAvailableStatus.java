package az.company.onlinelibrarysystem.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumAvailableStatus {

    ACTIVE(1),
    DEACTIVE(0);

    private int code;





}
