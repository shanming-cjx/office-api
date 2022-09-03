package com.chenjx.office.api.entity.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 *Spring Security自定义权限类
 */

@Data
@NoArgsConstructor
public class MyGrantedAuthority implements GrantedAuthority {
    private static final long serialVersionUID = 550L;
    private  String role;

    public MyGrantedAuthority(String role){
        this.role = role;
    }


    @Override
    public String getAuthority() {
        return this.role;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return obj instanceof MyGrantedAuthority ? this.role.equals(((MyGrantedAuthority)obj).role) : false;
        }
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    public String toString() {
        return this.role;
    }

}
