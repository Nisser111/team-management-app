import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Employee } from "../interfaces/Employee.interface";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { ApiResponse } from "../interfaces/ApiResponse.interface";

/**
 * Provides methods for managing employees in the application.
 * This service interacts with the server to perform CRUD operations on employees.
 */
@Injectable({
  providedIn: "root",
})
export class EmployeesService {
  private apiUrl = "http://localhost:8080"; // Base URL for the API

  constructor(private http: HttpClient) {}

  /**
   * Prepares the HTTP headers for requests.
   *
   * @returns An object containing the HTTP headers for the request.
   */
  private getHeaders() {
    return {
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
    };
  }

  /**
   * Retrieves all employees from the server.
   *
   * This method sends a GET request to the server to fetch all employees.
   * The response is then mapped to the ApiResponse interface, which includes the data, success status, and error message.
   *
   * @returns An Observable that resolves to an ApiResponse object containing an array of Employee objects.
   */
  getAll(): Observable<ApiResponse> {
    return this.http
      .get<ApiResponse>(`${this.apiUrl}/employees`, this.getHeaders())
      .pipe(map((response) => response));
  }

  /**
   * Deletes an employee by their ID from the server.
   *
   * This method sends a DELETE request to the server to remove an employee.
   * The request includes the ID of the employee to be deleted.
   * The response is then mapped to the ApiResponse interface, which includes the data, success status, and error message.
   *
   * @param id The ID of the employee to be deleted.
   * @returns An Observable that resolves to an ApiResponse object containing the deletion status.
   */
  deleteById(id: number): Observable<any> {
    return this.http
      .delete<ApiResponse>(`${this.apiUrl}/employees/${id}`, this.getHeaders())
      .pipe(map((response) => response));
  }

  /**
   * Adds a new employee to the server.
   *
   * This method sends a POST request to the server to create a new employee.
   * The request body includes the Employee object to be added.
   * The response is then mapped to the ApiResponse interface, which includes the data, success status, and error message.
   *
   * @param employee The Employee object to be added.
   * @returns An Observable that resolves to an ApiResponse object containing the added Employee object.
   */
  addNew(employee: Employee): Observable<ApiResponse> {
    return this.http
      .post<ApiResponse>(
        `${this.apiUrl}/employees`,
        employee,
        this.getHeaders()
      )
      .pipe(map((response) => response));
  }

  /**
   * Edits an existing employee by their ID on the server.
   *
   * This method sends a PATCH request to the server to update an existing employee.
   * The request body includes the updated Employee object.
   * The response is then mapped to the ApiResponse interface, which includes the data, success status, and error message.
   *
   * @param employee The Employee object with updated details.
   * @returns An Observable that resolves to an ApiResponse object containing the updated Employee object.
   */
  editById(employee: Employee): Observable<ApiResponse> {
    return this.http
      .patch<ApiResponse>(
        `${this.apiUrl}/employees/${employee.id}`,
        employee,
        this.getHeaders()
      )
      .pipe(map((response) => response));
  }
}
