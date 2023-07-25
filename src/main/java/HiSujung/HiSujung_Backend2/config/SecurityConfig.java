package HiSujung.HiSujung_Backend2.config;

import HiSujung.HiSujung_Backend2.entity.Role;
import HiSujung.HiSujung_Backend2.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    private final JwtTokenProvider jwtTokenProvider;

//    @Bean
//    public BCryptPasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


    //토큰 방식으로 인증을 하기 때문에 기존에 사용하던 폼로그인, 세션 비활성화
    http.
            cors()
            .and()
            .httpBasic().disable()
            .csrf().disable()
            .formLogin().disable()
            .logout().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


    //요청에 대한 권한 설정
//    http.authorizeRequests()
//            .antMatchers("/main", "/token").permitAll()
//            .antMatchers("/admin/**").hasRole(Role.ADMIN.name())
//            .anyRequest().authenticated();
//
    http
            .authorizeHttpRequests((authz) -> authz
                    .anyRequest().permitAll()
            )
            .httpBasic(withDefaults());
    return http.build();
}

    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
}
