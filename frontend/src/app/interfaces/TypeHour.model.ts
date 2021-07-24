import { Hour } from './Hour.model';

export interface TypeHour {
  regularHour: Hour;
  nightHour: Hour;
  sundayHour: Hour;
  extraRegularHour: Hour;
  extraNightHour: Hour;
  extraSundayHour: Hour;
}
