import { Injectable } from '@angular/core';

@Injectable()
export class FileUtil {
  public static isValidSize(size: number): boolean {
    return size / 1000000 < 10;
  }

  public static isImage(ext: string): boolean {
    return ['png', 'jpg', 'jpeg', 'bmp', 'gif', 'PNG', 'JPG', 'JPEG', 'BMP', 'GIF'].includes(ext);
  }

  public static isPdf(ext: string): boolean {
    return ['pdf'].includes(ext);
  }

  public static isCSV(ext: string): boolean {
    return ['csv'].includes(ext);
  }

  public static isSCORM(ext: string): string | boolean {
    return ext && ['zip'].includes(ext.toLowerCase());
  }
}
