import { FormBuilder, FormGroup, Validators } from "@angular/forms";

/**
 * Creates a FormGroup for the employee form.
 *
 * @param fb - The FormBuilder instance.
 * @returns A FormGroup representing the employee form.
 */
export function createEmployeeForm(fb: FormBuilder, id: number): FormGroup {
  return fb.group({
    id,
    firstName: [
      "",
      [Validators.required, Validators.minLength(2), Validators.maxLength(50)],
    ],
    lastName: [
      "",
      [Validators.required, Validators.minLength(2), Validators.maxLength(50)],
    ],
    email: ["", [Validators.required, Validators.email]],
    phone: [
      "",
      [
        Validators.required,
        Validators.pattern(
          /(?:([+]\d{1,4})[-.\s]?)?(?:[(](\d{1,3})[)][-.\s]?)?(\d{1,4})[-.\s]?(\d{1,4})[-.\s]?(\d{1,9})/
        ),
      ],
    ],
    hireDate: ["", Validators.required],
    role: ["", Validators.required],
    teamId: ["", [Validators.required, Validators.min(0)]],
  });
}
