import { ExtraSundayHour } from './ExtraSundayHour.model';
import { ExtraNightHour } from './ExtraNightHour.model';
import { ExtraRegularHour } from './ExtraRegularHour.model';
import { SundayHour } from './SundayHour.model';
import { NightHour } from './NightHour.model';
import { RegularHour } from './RegularHour.model';

export interface TypeHour {

  regularHour: RegularHour;
  nightHour: NightHour;
  sundayHour: SundayHour;
  extraRegularHour: ExtraRegularHour;
  extraNightHour: ExtraNightHour;
  extraSundayHour: ExtraSundayHour;

}
