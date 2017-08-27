import { SmDatabase } from './sm.database';

export class SmTableContent {

  columns: Map<string, string>;

  database: SmDatabase = new SmDatabase();

}
