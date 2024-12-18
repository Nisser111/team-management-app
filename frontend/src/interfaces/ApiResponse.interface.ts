import { Employee } from "./Employee.interface";
import { Team } from "./Team.interface";

export interface ApiResponse {
  success: boolean;
  message: string;
  data?: Team | Employee | [];
  error?: string;
}
