import { FormControl, ValidationErrors } from "@angular/forms";
import { FormGroup } from "@angular/forms";

export class EcommerceValidators {
  // whitespace validation
  static notOnlyWhitespace(control: FormControl): ValidationErrors {
    // check if string only contains whitespace
    if (control.value != null && control.value.trim().length === 0) {
      // invalid, return error object
      return { notOnlyWhitespace: true };
    } else {
      // valid, return null
      return null;
    }
  }

  static checkIfBlankValidator(control: FormGroup): { [s: string]: boolean } {
    if (
      control.value !== null &&
      control.value.trim() !== control.value &&
      control.value.trim() === ""
    ) {
      return { blank: true };
    }
    return null;
  }

  static notBlankValidator(control: FormGroup): { [s: string]: boolean } {
    // checks if the field is null or has an empty value in it.
    if (control.value === null || control.value.trim() === "") {
      return { blank: true };
    }
    return null;
  }

  static passwordMatchCheckValidator(control: FormGroup): {
    [s: string]: boolean;
  } {
    if (control.value.newPassword !== control.value.newPasswordConfirm) {
      return { noMatch: true };
    }
    return null;
  }
}
