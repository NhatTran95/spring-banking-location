package com.cg.model;
import com.cg.model.dto.response.CustomerResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted = 0")
@SQLDelete(sql = "UPDATE customers SET `deleted` = 1 WHERE (`id` = ?);")
public class Customer implements Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String phone;

    @Column(precision = 10, scale = 0, nullable = false, updatable = false)
    private BigDecimal balance;

    @OneToOne
    @JoinColumn(name = "location_region_id", referencedColumnName = "id", nullable = false)
    private LocationRegion locationRegion;

    @OneToMany(mappedBy = "sender")
    private List<Transfer> transferSender;

    @OneToMany(mappedBy = "recipient")
    private List<Transfer> transferRecipient;

    private Boolean deleted = false;

    public CustomerResDTO toCustomerResDTO() {
        return new CustomerResDTO()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setBalance(balance)
                .setLocationRegion(locationRegion.toLocationRegionResDTO());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Customer customer = (Customer) o;
        String fullName = customer.getFullName();
        if(fullName.length() == 0 ) {
            errors.rejectValue("fullName", "fullName.empty", "Vui lòng nhập họ tên đầy đủ");
        }else if(fullName.length()<6 || fullName.length()>20) {
            errors.rejectValue("fullName", "fullName.length", "Vui lòng nhập họ tên từ 6 đến 20 kí tự");
        }


        String email = customer.getEmail();

        if(email.length() == 0) {
            errors.rejectValue("email", "email.empty", "Vui lòng nhập email đầy đủ");
        }else if(!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errors.rejectValue("email", "email.matches", "Vui lòng nhập email theo định dạng 'xxx@xxx.xxx'");}


        String phone = customer.getPhone();
        if(phone.length() == 0) {
            errors.rejectValue("phone","phone.empty", "Vui lòng nhập số điện thoại đầy đủ");
        }

        String address = customer.locationRegion.getAddress();
        if(address.length() == 0) {
            errors.rejectValue("locationRegion.address", "address.empty", "Vui lòng nhập địa chỉ đầy đủ");
        }

    }
}
