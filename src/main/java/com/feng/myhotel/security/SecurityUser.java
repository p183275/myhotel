package com.feng.myhotel.security;

import com.feng.myhotel.entities.vo.UserVO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 实现UserDetails类
 * @author : pcf
 * @date : 2021/11/11 18:48
 */
@Data
public class SecurityUser implements UserDetails {

    //当前登录用户
    private transient UserVO currentUserInfo;

    //当前权限
    private List<String> permissionValueList;
    public SecurityUser() {
    }
    public SecurityUser(UserVO loginUserVO) {
        if (loginUserVO != null) {
            this.currentUserInfo = loginUserVO;
        }
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(String permissionValue : permissionValueList) {
            if(StringUtils.isEmpty(permissionValue)) continue;
            SimpleGrantedAuthority authority = new
                    SimpleGrantedAuthority(permissionValue);
            authorities.add(authority);
        }
        return authorities;
    }

    // 拿到当前登录用户
    public UserVO getLoginUserVO(){
        return currentUserInfo;
    }

    @Override
    public String getPassword() {
        return currentUserInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return currentUserInfo.getLoginAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
