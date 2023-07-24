package poc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InjectionController {

  private static final Logger logger = Logger.getLogger(InjectionController.class);

  @Autowired
  private DataSource dataSource;

  // call GET method such as http://localhost:8080/?param=');insert into logs values(':(
  // e.g.:
  //    curl 'http://localhost:8080/?param=%27);insert%20into%20logs%20values(%27:('
  @GetMapping("/")
  public String hello(String param) throws ClassNotFoundException, SQLException {

    // records log4j log message into in-memory DB
    logger.error("head "+ param + " tail");

    try (Statement st = dataSource.getConnection().createStatement()) {
      ResultSet rs = st.executeQuery("select msg from logs");
      int i = 1;
      String logs = "";
      while(rs.next()) {
        logs += i++ + ": " + rs.getString(1) + "<br/>\n";
      }
      return "Log entries: <br/>\n" + logs;
    }
  }

}
