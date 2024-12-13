import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Employee } from "../interfaces/Employee.interface";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class EmployeesService {
  private apiUrl = "http://localhost:8080";
  constructor(private http: HttpClient) {}

  getAll(): Observable<Employee[]> {
    const headers = {
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "*",
    };
    return this.http.get<Employee[]>(`${this.apiUrl}/employees`, { headers });
  }
}
