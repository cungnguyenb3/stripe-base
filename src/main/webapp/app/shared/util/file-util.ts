import { Injectable } from '@angular/core';

@Injectable()
export class FileUtil {
  public static isValidSize(size: number) {
    return size / 1000000 < 10;
  }

  public static isImage(ext: string) {
    return ['png', 'jpg', 'jpeg', 'bmp', 'gif', 'PNG', 'JPG', 'JPEG', 'BMP', 'GIF'].includes(ext);
  }

  public static isPdf(ext: string) {
    return ['pdf'].includes(ext);
  }

  public static isCSV(ext: string) {
    return ['csv'].includes(ext);
  }

  public static isSCORM(ext: string) {
    return ext && ['zip'].includes(ext.toLowerCase());
  }
}
