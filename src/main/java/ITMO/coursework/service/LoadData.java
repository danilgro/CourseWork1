package ITMO.coursework.service;

import ITMO.coursework.model.db.entity.document.Lesson;
import ITMO.coursework.model.db.entity.document.Certificate;
import ITMO.coursework.model.db.entity.document.Passport;
import ITMO.coursework.model.db.entity.people.Manager;
import ITMO.coursework.model.db.entity.people.Worker;
import ITMO.coursework.model.db.repository.document.LessonRepo;
import ITMO.coursework.model.db.repository.document.CertificateRepo;
import ITMO.coursework.model.db.repository.document.PassportRepo;
import ITMO.coursework.model.db.repository.people.ManagerRepo;
import ITMO.coursework.model.db.repository.people.WorkerRepo;
import ITMO.coursework.model.enums.ClassByJackWelch;
import ITMO.coursework.model.enums.Gender;
import ITMO.coursework.model.enums.Status;
import ITMO.coursework.model.enums.WelderLevel;
import ITMO.coursework.service.document.CertificateService;
import ITMO.coursework.service.document.PassportService;
import ITMO.coursework.service.people.WorkerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@Service
@AllArgsConstructor
public class LoadData {

    private final WorkerRepo workerRepo;
    private final ManagerRepo managerRepo;
    private final PassportRepo passportRepo;
    private final CertificateRepo certificateRepo;
    private final LessonRepo lessonRepo;

    private final CertificateService certificateService;

    private final PassportService passportService;
    private final WorkerService workerService;
    private final ObjectMapper mapper;
//    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void createData() {
        for (int i = 0; i < 5; i++) {
            Worker worker = getRandomWorker();
            //   worker.setPassword(passwordEncoder.encode("user"));
            getRandomPassport(worker);
            getRandomCertificate(worker);
        }

//        Manager admin = getRandomManager();
//        admin.setName("admin");
//        //    admin.setPassword(passwordEncoder.encode("admin"));
//        admin.setRoles("ROLE_ADMIN");
//        managerRepo.save(admin);

        Lesson welding = getRandomLesson();
        welding.setTitle("Аттестация сварщиков и специалистов сварочного производства");
        welding.setDescription(" Аттестацию персонала сварочного производства проводят по четырем уровням, соответствующим " +
                "осуществляемой кандидатом деятельности.");
        lessonRepo.save(welding);

        Lesson height= getRandomLesson();
        height.setTitle("Обучение мерам безопасности при работе на высоте");
        height.setDescription(" Работники, выполняющие работы на высоте, должны иметь квалификацию, соответствующую характеру" +
                " выполняемых работ. Уровень квалификации подтверждается документом о профессиональном образовании (обучении) и (или) о квалификации.");
        lessonRepo.save(height);
    }

    private Worker getRandomWorker() {
        Faker faker = new Faker();
        Worker worker = new Worker();
        worker.setFirstname(faker.name().name());
        worker.setSurname(faker.name().lastName());
        worker.setMiddleName(faker.name().lastName());
        worker.setDepartment(faker.job().title());
        worker.setPhone(faker.phoneNumber().cellPhone());
        worker.setEmail(faker.company().url());
        worker.setEducation(faker.job().keySkills());
        worker.setMedicalCertificate(faker.commerce().productName());
        worker.setProfession("Сварщик");
        worker.setNumberCertification("ЮР-10АЦ-II-01551");
        worker.setGender(Gender.MALE);
        worker.setStatus(Status.CREATED);
        worker.setRoles("ROLE_WORKER");
        worker.setPassword("user");
        worker.setClassByJackWelch(ClassByJackWelch.B);
        worker.setCreatedAt(LocalDateTime.now());
        worker.setBirthDay(LocalDate.of(2000, 8, 03));
        worker.setInn(faker.number().numberBetween(111111111111L, 999999999999L));
        worker.setSnils(faker.number().numberBetween(11111111111L, 99999999999L));
        worker.setSalary(faker.number().numberBetween(10000, 500000));
        worker.setQualificationLevel(faker.number().numberBetween(2, 8));
        worker.setDateCertification(LocalDate.of(2023, 4, 11));
        worker.setAge(faker.number().numberBetween(65, 20));
        workerRepo.save(worker);
        return worker;
    }

    private Passport getRandomPassport(Worker worker) {
        Faker faker = new Faker();
        Passport passport = new Passport();
        passport.setName(worker.getFirstname());
        passport.setSurname(worker.getSurname());
        passport.setMiddleName(worker.getMiddleName());
        passport.setBirthDate(worker.getBirthDay());
        passport.setNumber(faker.number().numberBetween(100000, 999999));
        passport.setSerial(faker.number().numberBetween(1000, 9999));
        passport.setIssued(LocalDate.of(2012, 3, 05));
        passport.setWho(faker.number().numberBetween(13, 50) + " отдел милиции");
        passport.setCountry(faker.address().country());
        passport.setCity(faker.address().city());
        passport.setStreet(faker.address().streetAddress());
        passport.setNumberHouse(faker.address().buildingNumber());
        passport.setStatus(Status.CREATED);
        passport.setCreatedAt(LocalDateTime.now());
        passportRepo.save(passport);
        worker.setPassports(Arrays.asList(passport)); // добавляем паспорт
        workerService.updatePassportList(worker); // обновляем данные у сотрудника
        passport.setWorker(worker);
        passportRepo.save(passport);
        return passport;
    }

    private Certificate getRandomCertificate(Worker worker) {
        Faker faker = new Faker();
        Certificate certificate = new Certificate();
        certificate.setName(worker.getFirstname());
        certificate.setSurname(worker.getSurname());
        certificate.setMiddleName(worker.getMiddleName());
        certificate.setWho("НАКС");
        certificate.setIssued(LocalDate.of(2012, 3, 05));
        certificate.setValidUntil(LocalDate.of(2014, 3, 5));
        certificate.setWelderLevel(WelderLevel.WELDER_LEVEL_2);
        certificate.setProtocolNumber("4562-Ap");
        certificate.setCodeBrand("33R8");
        certificateRepo.save(certificate);
        worker.setCertificates(Arrays.asList(certificate)); // добавляем паспорт
        workerService.updatePassportList(worker); // обновляем данные у сотрудника
        certificateRepo.save(certificate);
        return certificate;
    }

    private Manager getRandomManager() {
        Faker faker = new Faker();
        Manager manager = new Manager();
        manager.setFirstname(faker.name().name());
        manager.setSurname(faker.name().lastName());
        manager.setMiddleName(faker.name().lastName());
        manager.setDepartment(faker.job().title());
        manager.setPhone(faker.phoneNumber().cellPhone());
        manager.setEmail(faker.company().url());
        manager.setEducation(faker.job().keySkills());
        manager.setGender(Gender.FEMALE);
        manager.setRoles("ROLE_ADMIN");
        manager.setPassword("admin");
        manager.setName("admin");
        manager.setClassByJackWelch(ClassByJackWelch.B);
        manager.setCreatedAt(LocalDateTime.now());
        manager.setBirthDay(LocalDate.of(2000, 8, 03));
        manager.setInn(faker.number().numberBetween(111111111111L, 999999999999L));
        manager.setSnils(faker.number().numberBetween(11111111111L, 99999999999L));
        manager.setSalary(faker.number().numberBetween(10000, 500000));
        manager.setQualificationLevel(faker.number().numberBetween(2, 8));
        manager.setAge(faker.number().numberBetween(65, 20));
        manager.setPosition(faker.job().seniority());
        manager.setProfessionalExperience(faker.number().numberBetween(0, 35));
        manager.setStatus(Status.CREATED);
        managerRepo.save(manager);
        return manager;
    }

    private Lesson getRandomLesson() {
        Faker faker = new Faker();
        Lesson lesson = new Lesson();
        lesson.setUrl(faker.company().url());
        lessonRepo.save(lesson);
        return lesson;
    }

}
