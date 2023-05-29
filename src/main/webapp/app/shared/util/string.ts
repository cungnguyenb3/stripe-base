export function isNullOrUndefined(value: any): boolean {
  return value === null || value === undefined;
}

export function isEmptyString(value: any): boolean {
  return value === null || value === undefined || value === '';
}

export function countWords(str: string): number {
  if (isNullOrUndefined(str)) {
    return 0;
  }
  const arr = str.split(' ');
  return arr.filter(word => word !== '').length;
}
