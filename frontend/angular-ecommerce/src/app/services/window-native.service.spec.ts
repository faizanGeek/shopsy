import { TestBed } from '@angular/core/testing';

import { WindowNativeService } from './window-native.service';

describe('WindowNativeService', () => {
  let service: WindowNativeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WindowNativeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
