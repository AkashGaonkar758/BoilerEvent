package com.example.campusevents.BootStrap;

import com.example.campusevents.Event.Model.Event;
import com.example.campusevents.Event.Repo.EventRepository;
import com.example.campusevents.User.Model.User;
import com.example.campusevents.User.repo.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
// @Profile("dev")
public class DevDataSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final PasswordEncoder passwordEncoder;

    public DevDataSeeder(UserRepository userRepository,
                         EventRepository eventRepository,
                         PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {


        User ava   = upsertUser("ava.lee",     "Ava",   "Lee",    "alee@purdue.edu",
                true,  User.Year.SOPHOMORE, User.Role.ROLE_USER, "password123");
        User jay   = upsertUser("jay.patel",   "Jay",   "Patel",  "jpatel@purdue.edu",
                false, User.Year.FRESHMAN,  User.Role.ROLE_USER, "password123");
        User zoe   = upsertUser("zoe.lo",      "Zoe",   "Lopez",  "zlopez@purdue.edu",
                false, User.Year.JUNIOR,    User.Role.ROLE_USER, "password123");
        User tariq = upsertUser("tariq.ah",    "Tariq", "Ahmad",  "tahmad@purdue.edu",
                true,  User.Year.SENIOR,    User.Role.ROLE_USER, "password123");
        User sloan = upsertUser("sloan.v",     "Sloan", "Vang",   "svang@purdue.edu",
                true,  User.Year.JUNIOR,    User.Role.ROLE_USER, "password123");
        User nikhil= upsertUser("nikhil.codes","Nikhil","Desai",  "ndesai@purdue.edu",
                false, User.Year.SENIOR,    User.Role.ROLE_USER, "password123");
        User emily = upsertUser("em.cho",      "Emily", "Cho",    "echo@purdue.edu",
                false, User.Year.SOPHOMORE, User.Role.ROLE_USER, "password123");
        User admin = upsertUser("admin",       "Admin", "User",   "admin@example.com",
                true,  User.Year.SENIOR,    User.Role.ROLE_ADMIN,"admin123");

        LocalDateTime now = LocalDateTime.now();


        Event e1 = upsertEvent(
                "Late-Night Study Jam (CS 180/182)",
                "Peer-led problem grind. Bring laptops; we’ll rotate helpers every 30 min.",
                "https://commons.wikimedia.org/wiki/Special:FilePath/Lawson_Computer_Science_Building.jpg",
                "Lawson Commons",
                Event.EventType.OPEN_TO_ALL,
                now.plusDays(1).withHour(20).withMinute(0),
                now.plusDays(1).withHour(23).withMinute(0),
                ava
        );

        Event e2 = upsertEvent(
                "Purdue Hackers Build Night",
                "Ship a tiny thing in 3 hours. No experience needed; pizza after demos.",
                "https://commons.wikimedia.org/wiki/Special:FilePath/Lawson_Computer_Science_Building.jpg",
                "Lawson 3102",
                Event.EventType.OPEN_TO_ALL,
                now.plusDays(2).withHour(18).withMinute(30),
                now.plusDays(2).withHour(21).withMinute(30),
                jay
        );

        Event e3 = upsertEvent(
                "CoRec 3v3 Basketball Pickup",
                "Casual runs. Winners stay, bring a dark/light tee.",
                "https://commons.wikimedia.org/wiki/Special:FilePath/France_A_C%C3%B3rdova_Recreational_Sports_Center_Purdue_University_2016_01.jpg",
                "CoRec Courts",
                Event.EventType.OPEN_TO_ALL,
                now.plusDays(3).withHour(19).withMinute(0),
                now.plusDays(3).withHour(21).withMinute(0),
                tariq
        );

        Event e4 = upsertEvent(
                "Horticulture Park Sunset Picnic",
                "Chill vibes, blankets, playlists. We’ll bring frisbees.",
                "https://commons.wikimedia.org/wiki/Special:FilePath/Purdue_Hort_Gardens.jpg",
                "Horticulture Park",
                Event.EventType.OPEN_TO_ALL,
                now.plusDays(4).withHour(18).withMinute(0),
                now.plusDays(4).withHour(20).withMinute(0),
                zoe
        );

        Event e5 = upsertEvent(
                "AAPI Night Market (Student Orgs)",
                "Food tents, org booths, and performances — all student-run.",
                "https://commons.wikimedia.org/wiki/Special:FilePath/Purdue_MainCampus.jpg",
                "Memorial Mall",
                Event.EventType.OPEN_TO_ALL,
                now.plusDays(5).withHour(17).withMinute(0),
                now.plusDays(5).withHour(20).withMinute(0),
                sloan
        );

        Event e6 = upsertEvent(
                "IEEE Soldering 101 (Hands-On)",
                "Learn to solder a simple kit. Limited seats — first come first served.",
                "https://commons.wikimedia.org/wiki/Special:FilePath/Neil_Armstrong_Hall_of_Engineering_Purdue_University_2016_05.jpg",
                "EE 170 Lab",
                Event.EventType.MEMBER_ONLY,
                now.plusDays(6).withHour(18).withMinute(0),
                now.plusDays(6).withHour(20).withMinute(0),
                nikhil
        );

        Event e7 = upsertEvent(
                "Boiler Bhangra Auditions",
                "No experience required — learn basics, then quick tryout sets.",
                "https://commons.wikimedia.org/wiki/Special:FilePath/Purdue_Memorial_Union_Great_Hall.png",
                "PMU North Ballroom",
                Event.EventType.OPEN_TO_ALL,
                now.plusDays(7).withHour(19).withMinute(0),
                now.plusDays(7).withHour(21).withMinute(0),
                ava
        );

        Event e8 = upsertEvent(
                "Chalk the Mall: Org Promo Night",
                "Grab chalk and promo a student org. Art + info, keep it friendly.",
                "https://commons.wikimedia.org/wiki/Special:FilePath/Engineering_Fountain_Purdue_University_2016_02.jpg",
                "Purdue Mall (by Engineering Fountain)",
                Event.EventType.OPEN_TO_ALL,
                now.plusDays(8).withHour(16).withMinute(0),
                now.plusDays(8).withHour(18).withMinute(0),
                emily
        );

        Event e9 = upsertEvent(
                "Game Day Student Watch Party",
                "Purdue vs. rival — bring your crew, face paint optional.",
                "https://commons.wikimedia.org/wiki/Special:FilePath/Ross-Ade_Stadium.jpg",
                "South lawn near Ross–Ade",
                Event.EventType.OPEN_TO_ALL,
                now.plusDays(9).withHour(18).withMinute(0),
                now.plusDays(9).withHour(22).withMinute(0),
                jay
        );

        Event e10 = upsertEvent(
                "Bell Tower Golden Hour Photos",
                "Photo circle for new profile pics. We’ll rotate shooters & subjects.",
                "https://commons.wikimedia.org/wiki/Special:FilePath/Purdue_University_Bell_Tower.jpg",
                "Bell Tower Lawn",
                Event.EventType.OPEN_TO_ALL,
                now.plusDays(10).withHour(19).withMinute(0),
                now.plusDays(10).withHour(20).withMinute(30),
                zoe
        );


        e1.getLikedByUsers().add(ava);
        e1.getLikedByUsers().add(jay);
        e1.getLikedByUsers().add(admin);

// e2
        e2.getLikedByUsers().add(jay);
        e2.getLikedByUsers().add(zoe);

// e3
        e3.getLikedByUsers().add(tariq);
        e3.getLikedByUsers().add(sloan);
        e3.getLikedByUsers().add(admin);

// e4
        e4.getLikedByUsers().add(emily);
        e4.getLikedByUsers().add(ava);
        e4.getLikedByUsers().add(nikhil);

// e5
        e5.getLikedByUsers().add(nikhil);

// e6
        e6.getLikedByUsers().add(zoe);
        e6.getLikedByUsers().add(sloan);
        e6.getLikedByUsers().add(emily);

// e7
        e7.getLikedByUsers().add(jay);
        e7.getLikedByUsers().add(nikhil);
        e7.getLikedByUsers().add(admin);
        e7.getLikedByUsers().add(ava);

// e8
        e8.getLikedByUsers().add(emily);
        e8.getLikedByUsers().add(zoe);
        e8.getLikedByUsers().add(tariq);

// e9
        e9.getLikedByUsers().add(tariq);
        e9.getLikedByUsers().add(sloan);

// e10
        e10.getLikedByUsers().add(ava);
        e10.getLikedByUsers().add(jay);
        e10.getLikedByUsers().add(zoe);
        e10.getLikedByUsers().add(tariq);
        e10.getLikedByUsers().add(sloan);
        e10.getLikedByUsers().add(nikhil);
        e10.getLikedByUsers().add(emily);
        e10.getLikedByUsers().add(admin);

        eventRepository.save(e1);
        eventRepository.save(e2);
        eventRepository.save(e3);
        eventRepository.save(e4);
        eventRepository.save(e5);
        eventRepository.save(e6);

        eventRepository.save(e7);
        eventRepository.save(e8);
        eventRepository.save(e9);
        eventRepository.save(e10);
        eventRepository.save(e5);
        eventRepository.save(e6);
    }

    private User upsertUser(String username,
                            String firstName,
                            String lastName,
                            String email,
                            boolean verified,
                            User.Year year,
                            User.Role role,
                            String rawPassword) {

        Optional<User> existing = userRepository.findByUsername(username);
        User u = existing.orElseGet(User::new);

        u.setUsername(username);
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setEmail(email);
        u.setVerified(verified);
        u.setYear(year);
        u.setRole(role);
        if (u.getId() == null) {
            u.setPassword(passwordEncoder.encode(rawPassword));
            u.setCreatedAt(LocalDateTime.now()); // required: createdAt is non-nullable
        }
        return userRepository.save(u);
    }

    private Event upsertEvent(String name,
                              String description,
                              String imageUrl,
                              String location,
                              Event.EventType type,
                              LocalDateTime start,
                              LocalDateTime end,
                              User postedBy) {

        Optional<Event> existing = eventRepository.findByName(name);
        Event e = existing.orElseGet(Event::new);

        e.setName(name);
        e.setDescription(description);
        e.setImageUrl(imageUrl);
        e.setLocation(location);
        e.setType(type);
        e.setEventDate(start);
        e.setEventEndDate(end);
        e.setPostedBy(postedBy);

        // Recompute active each run to keep it consistent.
        e.setActive(start.isAfter(LocalDateTime.now()));

        return eventRepository.save(e);
    }
}
