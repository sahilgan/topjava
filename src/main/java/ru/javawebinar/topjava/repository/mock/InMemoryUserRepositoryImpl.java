package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;


import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

//@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private List<User> users = Arrays.asList(new User(0, "userx", "w@w.ru", "sss", Role.ROLE_ADMIN));

    public Map<Integer, User> repository = new ConcurrentHashMap<>();

    private AtomicInteger counter = new AtomicInteger(0);


    public InMemoryUserRepositoryImpl() {
        users.forEach(x -> repository.put(0, x));
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        if (repository.containsKey(id)) {
            repository.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (repository.entrySet().stream().anyMatch(x -> x.getValue().getName().equals(user.getName()))) {
            Integer newId = counter.incrementAndGet();
            user.setId(newId);
            repository.put(newId, user);
        }
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return repository.entrySet().stream().map(Map.Entry::getValue).collect(toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);

        if (repository.entrySet().stream().anyMatch(y -> y.getValue().getEmail().equals(email))) {
            return repository.entrySet().stream().map(Map.Entry::getValue).filter(y-> y.getEmail().equals(email)).collect(toList()).get(0);
        }
        else {
            throw new NotFoundException("User not found");
        }
    }

    public static void main(String[] args) {
        InMemoryUserRepositoryImpl x = new InMemoryUserRepositoryImpl();

        User u1 = new User(0, "userx", "w@w.ru", "sss", Role.ROLE_ADMIN);
        x.save(u1);

        List<User> x1 = x.getAll();
    }
}
