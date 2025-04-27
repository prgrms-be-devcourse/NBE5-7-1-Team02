package easy.gc_coffee_api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitAdminRunner implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> usernameCandidate = args.getOptionValues("username");
        List<String> passwordCandidate = args.getOptionValues("password");
        if (!hasUserInfo(usernameCandidate, passwordCandidate)) {
            return;
        }

        String userName = getUserName(usernameCandidate);
        String password = getPassword(passwordCandidate);
        if (hasAdmin()) {
            deleteAllAdmin();
        }

        insertAdmin(userName, password);
    }

    private void insertAdmin(String userName, String password) {
        LocalDateTime now = LocalDateTime.now();
        jdbcTemplate.update("insert into user (created_at,updated_at,username, password) values (?, ?, ?, ?)", ps -> {
            ps.setTimestamp(1, Timestamp.valueOf(now));
            ps.setTimestamp(2, Timestamp.valueOf(now));
            ps.setString(3, userName);
            ps.setString(4, passwordEncoder.encode(password));
        });
    }

    private static String getPassword(List<String> passwordCandidate) {
        return passwordCandidate.getLast();
    }

    private static String getUserName(List<String> usernameCandidate) {
        return usernameCandidate.getLast();
    }

    private void deleteAllAdmin() {
        jdbcTemplate.execute("delete from user");
    }

    private boolean hasAdmin() {
        Long count = jdbcTemplate.queryForObject("select count(*) from user", Long.class);
        return count > 0;
    }

    private static boolean hasUserInfo(List<String> username, List<String> password) {
        return username != null && !username.isEmpty() && password != null && !password.isEmpty();
    }
}
