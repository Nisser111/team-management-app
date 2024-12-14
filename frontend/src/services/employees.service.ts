import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Employee } from "../interfaces/Employee.interface";
import { map, Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class EmployeesService {
  private apiUrl = "http://localhost:8080";
  constructor(private http: HttpClient) {}

  /**
   * Retrieves all employees from the server.
   *
   * @returns An Observable of an array of Employee objects.
   */
  getAll(): Observable<Employee[]> {
    const headers = {
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "*",
    };
    return this.http.get<Employee[]>(`${this.apiUrl}/employees`, { headers });
  }

  /**
   * Deletes an employee by their ID from the server.
   *
   * @param id The ID of the employee to be deleted.
   * @returns An Observable of the response from the server, which can be either a JSON object or a plain text message.
   */
  deleteById(id: number): Observable<any> {
    return this.http
      .delete(`${this.apiUrl}/employees/${id}`, { responseType: "text" })
      .pipe(
        map((response: string) => {
          try {
            // Attempt to parse plain text as JSON
            return JSON.parse(response);
          } catch (error) {
            // If not JSON, return plain text
            return { message: response };
          }
        })
      );
  }

  /**
   * Adds a new employee to the server.
   *
   * @param employee The Employee object to be added.
   * @returns An Observable of the response from the server, which can be either a JSON object or a plain text message.
   */
  addNew(employee: Employee): Observable<any> {
    return this.http.post<string>(`${this.apiUrl}/employees`, employee, {
      responseType: "text" as "json",
    });
  }
}
